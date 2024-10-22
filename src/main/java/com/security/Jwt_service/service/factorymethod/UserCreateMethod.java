package com.security.Jwt_service.service.factorymethod;

import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.user.UserResponseFactory;

public interface UserCreateMethod {
    UserResponseFactory createUser(UserCreateDto userCreateDto);
}
