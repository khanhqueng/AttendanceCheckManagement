package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.teacher.TeacherCreateDto;
import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;
import com.security.Jwt_service.dto.response.user.UserResponseDto;
import com.security.Jwt_service.dto.response.user.UserResponseFactory;
import com.security.Jwt_service.entity.user.Role;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.Teacher;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.mapper.teacher.TeacherMapper;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.TeacherRepository;
import com.security.Jwt_service.service.TeacherService;
import com.security.Jwt_service.service.factorymethod.UserCreateMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService , UserCreateMethod {
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;
    private final TeacherMapper teacherMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Override
//    public TeacherResponseDto createTeacher(TeacherCreateDto createDto) {
//        if(teacherRepository.existsByEmailOrTeacherCode(createDto.getEmail(), createDto.getTeacherCode())){
//            throw new ResourceDuplicateException("Teacher", "email or teacher code", createDto.getTeacherCode()+" "+ createDto.getEmail());
//        }
//        Teacher teacher= teacherMapper.requestToEntity(createDto);
//        passwordEncoder= new BCryptPasswordEncoder();
//        Random random= new Random();
//        String resetCode = String. format("%04d", random.nextInt(10000));
//        Role role= roleRepository.findByName("TEACHER").get();
//        User user = User.builder()
//                .username(createDto.getTeacherCode())
//                .password(passwordEncoder.encode("12345678"))
//                .resetPasswordCode(resetCode)
//                .role(role)
//                .build();
//        teacher.setUser(user);
//        return teacherMapper.entityToResponse(teacherRepository.save(teacher));
//    }

    @Override
    public UserResponseFactory createUser(UserCreateDto userCreateDto) {
        if(teacherRepository.existsByEmailOrTeacherCode(userCreateDto.getEmail(), userCreateDto.getRoleCode())){
            throw new ResourceDuplicateException("Teacher", "email or teacher code", userCreateDto.getEmail()+" "+ userCreateDto.getRoleCode());
        }
        Teacher teacher= teacherMapper.requestToEntity(userCreateDto);
        passwordEncoder= new BCryptPasswordEncoder();
        Random random= new Random();
        String resetCode = String. format("%04d", random.nextInt(10000));
        Role role= roleRepository.findByName("TEACHER").get();
        User user = User.builder()
                .username(userCreateDto.getRoleCode())
                .password(passwordEncoder.encode("12345678"))
                .resetPasswordCode(resetCode)
                .role(role)
                .build();
        teacher.setUser(user);
        return teacherMapper.entityToResponseFactory(teacherRepository.save(teacher));
    }

    @Override
    public List<TeacherResponseDto> getAllTeachers() {
        return teacherRepository.findAll().stream().map(teacherMapper::entityToResponse).toList();
    }
}
