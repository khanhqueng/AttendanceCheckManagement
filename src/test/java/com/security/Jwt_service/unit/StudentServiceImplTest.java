package com.security.Jwt_service.unit;

import com.security.Jwt_service.dto.request.student.SearchAttendanceHistoryDto;
import com.security.Jwt_service.dto.response.student.StudentAttendanceHistoryResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.student.StudentMapper;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ClassroomRepository classroomRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStudent_shouldReturnList() {
        when(studentRepository.findAll()).thenReturn(java.util.Collections.emptyList());
        assertNotNull(studentService.getAllStudent());
    }

    @Test
    void searchStudent_shouldThrowException_whenStartDateAfterEndDate() {
        assertThrows(AppApiException.class, () -> studentService.searchStudent("code", java.time.LocalDate.now(), java.time.LocalDate.now().minusDays(1)));
    }

    @Test
    void getAllStudentWithViolation_shouldThrowException_whenClassroomNotFound() {
        when(classroomRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> studentService.getAllStudentWithViolation(1L));
    }
} 