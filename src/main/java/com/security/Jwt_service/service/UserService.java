package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.user.UserResponseDto;
import com.security.Jwt_service.service.factorymethod.UserCreateMethod;

public interface UserService {
//    UserResponseDto createUser(UserCreateDto createDto);
    UserResponseDto getUserById(Long userId);
    UserResponseDto updateUser(Long userId, String email);
    UserResponseDto changeRole(Long userId, String nameRole);
    UserCreateMethod createUserMethod(UserCreateDto userCreateDto);
}
