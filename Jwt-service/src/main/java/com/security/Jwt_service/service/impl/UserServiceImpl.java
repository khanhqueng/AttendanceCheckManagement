package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.mapper.UserMapper;
import com.security.Jwt_service.dto.request.UserCreateDto;
import com.security.Jwt_service.dto.response.UserResponseDto;
import com.security.Jwt_service.entity.user.Role;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.UserRepository;
import com.security.Jwt_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserCreateDto createDto) {
        if(userRepository.existsByUsername(createDto.getUsername())){
            throw new ResourceDuplicateException("User", "username", createDto.getUsername());
        }
        User user= mapper.mapToEntity(createDto);
        passwordEncoder= new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(createDto.getPassword()));
        Set<Role> roles= new HashSet<>();
        roles.add(roleRepository.findByName("USER").get());
        user.setRoles(roles);
        return  mapper.mapToResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        return mapper.mapToResponseDto(user);
    }
}
