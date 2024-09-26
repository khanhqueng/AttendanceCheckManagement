package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.SignInRequestDto;
import com.security.Jwt_service.dto.response.TokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    TokenResponseDto authenticate(SignInRequestDto signInRequestDto);

    TokenResponseDto refresh(HttpServletRequest request);
}
