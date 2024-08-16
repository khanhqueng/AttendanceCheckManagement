package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.UserCreateDto;
import com.security.Jwt_service.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserCreateDto createDto);
    UserResponseDto getUserById(Long userId);
}
