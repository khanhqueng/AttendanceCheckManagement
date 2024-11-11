package com.security.Jwt_service.exception.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.Jwt_service.dto.message.ErrorDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;
    public CustomAccessDeniedHandler() {
        this.objectMapper = new ObjectMapper();
    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErrorDetails errorDetails= new ErrorDetails(new Date(), accessDeniedException.getMessage(), "null");
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
