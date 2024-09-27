package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.teacher.TeacherCreateDto;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;
import com.security.Jwt_service.entity.user.Role;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.Teacher;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.mapper.teacher.TeacherMapper;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.TeacherRepository;
import com.security.Jwt_service.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;
    private final TeacherMapper teacherMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public TeacherResponseDto createTeacher(TeacherCreateDto createDto) {
        if(teacherRepository.existsByEmailOrTeacherCode(createDto.getEmail(), createDto.getTeacherCode())){
            throw new ResourceDuplicateException("Teacher", "email or teacher code", createDto.getTeacherCode()+" "+ createDto.getEmail());
        }
        Teacher teacher= teacherMapper.requestToEntity(createDto);
        passwordEncoder= new BCryptPasswordEncoder();
        Random random= new Random();
        String resetCode = String. format("%04d", random.nextInt(10000));
        Role role= roleRepository.findByName("TEACHER").get();
        User user = User.builder()
                .username(createDto.getTeacherCode())
                .password(passwordEncoder.encode("12345678"))
                .resetPasswordCode(resetCode)
                .role(role)
                .build();
        teacher.setUser(user);
        return teacherMapper.entityToResponse(teacherRepository.save(teacher));
    }
}
