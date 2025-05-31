package com.security.Jwt_service.unit;

import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.session.SessionMapper;
import com.security.Jwt_service.repository.AttendanceRepository;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.repository.SessionRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.impl.SessionServiceImpl;
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
class SessionServiceImplTest {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private SessionMapper sessionMapper;
    @Mock
    private ClassroomRepository classroomRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private SessionServiceImpl sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSessions_shouldThrowException_whenSessionExists() {
        SessionCreateDto dto = new SessionCreateDto();
        when(sessionRepository.existsByClassroomId(any())).thenReturn(true);
        assertThrows(ResourceDuplicateException.class, () -> sessionService.createSessions(dto, 1));
    }

    @Test
    void deleteSession_shouldThrowException_whenNotFound() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> sessionService.deleteSession(1L));
    }
} 