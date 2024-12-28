package com.security.Jwt_service.exception.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.Jwt_service.dto.message.ErrorDetails;
import com.security.Jwt_service.exception.JwtValidateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    public JwtAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();
    }
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        Throwable cause = authException.getCause();
        if (cause instanceof JwtValidateException jwtException){
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorDetails jwtErrorDetails= new ErrorDetails(new Date(), jwtException.getMessage(), "null");
            response.getWriter().write(objectMapper.writeValueAsString(jwtErrorDetails));
        }
        else{
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ErrorDetails errorDetails= new ErrorDetails(new Date(), authException.getMessage(), "null");
            response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
        }

    }
}
