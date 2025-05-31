package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.entity.user.Teacher;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.mapper.teacher.TeacherMapper;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class TeacherServiceImplTest {
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private TeacherMapper teacherMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldThrowException_whenDuplicate() {
        UserCreateDto dto = new UserCreateDto();
        when(teacherRepository.existsByEmailOrTeacherCode(any(), any())).thenReturn(true);
        assertThrows(ResourceDuplicateException.class, () -> teacherService.createUser(dto));
    }
} 