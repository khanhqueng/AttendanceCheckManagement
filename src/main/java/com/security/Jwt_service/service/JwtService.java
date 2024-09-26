package com.security.Jwt_service.service;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.util.TokenType;

public interface JwtService {
    String generateAccessToken(CustomUserDetails userDetails);
    Long extractUserid(String token, TokenType type);
    boolean isValidAccessToken(String token);
    boolean isValidRefreshToken(String token);
    String generateRefreshToken(CustomUserDetails userDetails);
}
