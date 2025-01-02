package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.auth.AuthRefreshToken;
import com.security.Jwt_service.dto.request.user.SignInRequestDto;
import com.security.Jwt_service.dto.response.auth.TokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    TokenResponseDto authenticate(SignInRequestDto signInRequestDto);

    TokenResponseDto refresh(AuthRefreshToken request);
}
