package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.entity.user.Role;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.student.StudentMapper;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public StudentResponseDto createStudent(StudentCreateDto createDto) {
        if(studentRepository.existsByEmailOrStudentCode(createDto.getEmail(), createDto.getStudentCode())){
            throw new ResourceDuplicateException("Student", "email or student code", createDto.getStudentCode()+" "+ createDto.getEmail());
        }
        Student student= studentMapper.requestToEntity(createDto);
        passwordEncoder= new BCryptPasswordEncoder();
        Random random= new Random();
        String resetCode = String. format("%04d", random.nextInt(10000));
        Role role= roleRepository.findByName("STUDENT").get();
        User user = User.builder()
                .username(createDto.getStudentCode())
                .password(passwordEncoder.encode("12345678"))
                .resetPasswordCode(resetCode)
                .role(role)
                .build();
        student.setUser(user);
        return studentMapper.entityToResponse(studentRepository.save(student));
    }
}
