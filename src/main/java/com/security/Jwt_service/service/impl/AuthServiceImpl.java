package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.request.auth.AuthRefreshToken;
import com.security.Jwt_service.dto.request.user.SignInRequestDto;
import com.security.Jwt_service.dto.response.auth.TokenResponseDto;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.repository.UserRepository;
import com.security.Jwt_service.service.AuthService;
import com.security.Jwt_service.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.security.Jwt_service.util.TokenType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Override
    public TokenResponseDto authenticate(SignInRequestDto signInRequestDto) {
        Authentication authentication=  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDto.getUsername(),signInRequestDto.getPassword()));
        String accessToken= jwtService.generateAccessToken((CustomUserDetails) authentication.getPrincipal());
        String refreshToken= jwtService.generateRefreshToken((CustomUserDetails) authentication.getPrincipal());
        return TokenResponseDto.builder()
                .accToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponseDto refresh(AuthRefreshToken request) {
        String refreshToken= request.getRefreshToken();
        if(StringUtils.hasText(refreshToken) && jwtService.isValidRefreshToken(refreshToken)){
            Long userId= jwtService.extractUserid(refreshToken, TokenType.REFRESH_TOKEN);
            User user= userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User not found"));
            CustomUserDetails customUserDetails= new CustomUserDetails(user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority(user.getRole().getName())));
            String newAccessToken = jwtService.generateAccessToken(customUserDetails);
            return TokenResponseDto.builder()
                    .accToken(newAccessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        return null;
    }
}
