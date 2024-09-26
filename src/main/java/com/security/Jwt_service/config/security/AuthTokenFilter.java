package com.security.Jwt_service.config.security;

import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.repository.UserRepository;
import com.security.Jwt_service.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.security.Jwt_service.util.TokenType;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token= getTokenFromRequest(request);
        if(StringUtils.hasText(token) && jwtService.isValidAccessToken(token)){
            Long userId= jwtService.extractUserid(token, TokenType.ACCESS_TOKEN);
            System.out.println(userId);
            String username= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId)).getUsername();
            UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

            filterChain.doFilter(request,response);
    }
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken= request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            String[] strings= bearerToken.split(" ");
            return strings[1];
        }
        return null;
    }

}
