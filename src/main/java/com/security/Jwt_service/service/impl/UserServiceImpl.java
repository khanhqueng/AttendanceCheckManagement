package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.user.UserResponseDto;
import com.security.Jwt_service.entity.user.Role;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.student.StudentMapper;
import com.security.Jwt_service.mapper.teacher.TeacherMapper;
import com.security.Jwt_service.mapper.user.UserMapper;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.repository.TeacherRepository;
import com.security.Jwt_service.repository.UserRepository;
import com.security.Jwt_service.service.StudentService;
import com.security.Jwt_service.service.UserService;
import com.security.Jwt_service.service.factorymethod.UserCreateMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Override
//    public UserResponseDto createUser(UserCreateDto createDto) {
//        if(userRepository.existsByUsername(createDto.getUsername())){
//            throw new ResourceDuplicateException("User", "username", createDto.getUsername());
//        }
//        User user= userMapper.requestToEntity(createDto);
//        passwordEncoder= new BCryptPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(createDto.getPassword()));
//        Role role= Role.builder().name(createDto.getRolName()).build();
//        user.setRole(role);
//        return  userMapper.entityToResponse(userRepository.save(user));
//    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        return userMapper.entityToResponse(user);
    }

    @Override
    public UserResponseDto updateUser(Long userId, String email) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDto changeRole(Long userId, String nameRole) {
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        Role newRole= roleRepository.findByName(nameRole).orElseThrow(
                ()-> new ResourceNotFoundException("Role", "name", nameRole)
        );
        user.setRole(newRole);
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    public UserCreateMethod createUserMethod(UserCreateDto userCreateDto) {
        if(userCreateDto.getRoleName().equals("Student")) return new StudentServiceImpl(studentRepository,studentMapper,roleRepository);
        else if(userCreateDto.getRoleName().equals("Teacher")) return new TeacherServiceImpl(teacherRepository,roleRepository,teacherMapper);
        return null;
    }
}
