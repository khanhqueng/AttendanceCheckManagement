package com.security.Jwt_service.dto.mapper;

import com.security.Jwt_service.dto.request.UserCreateDto;
import com.security.Jwt_service.dto.response.UserResponseDto;
import com.security.Jwt_service.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component

public class UserMapper {
    @Autowired
    private ModelMapper mapper;
    public User mapToEntity(UserCreateDto createDto){
        User user= mapper.map(createDto, User.class);
        return user;
    }
    public UserResponseDto mapToResponseDto(User user){
        UserResponseDto userResponseDto= mapper.map(user, UserResponseDto.class);
        return userResponseDto;
    }
}
