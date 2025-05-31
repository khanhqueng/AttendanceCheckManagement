package com.security.Jwt_service.unit;

import com.security.Jwt_service.dto.request.email.SendBatchDto;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class EmailServiceImplTest {
    @Mock
    private ClassroomRepository classroomRepository;
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendAlertToMultipleStudent_shouldThrowException_whenClassroomNotFound() {
        when(classroomRepository.findById(anyLong())).thenReturn(Optional.empty());
        SendBatchDto dto = new SendBatchDto();
        assertThrows(ResourceNotFoundException.class, () -> emailService.sendAlertToMultipleStudent(1L, dto));
    }
} 