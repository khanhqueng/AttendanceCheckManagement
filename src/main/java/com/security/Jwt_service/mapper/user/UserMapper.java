package com.security.Jwt_service.mapper.user;

import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.user.UserResponseDto;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends GenericMapper<User,UserCreateDto,UserResponseDto> {
//    @Override
//    @Mapping(target = "username", source = "request.name")
//    User requestToEntity(UserCreateDto request);
//
//    @Override
//    @Mapping(target = "name",source = "entity.username")
//     UserResponseDto entityToResponse(User entity);
}
