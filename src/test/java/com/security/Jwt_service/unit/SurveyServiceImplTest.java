package com.security.Jwt_service.unit;

import com.security.Jwt_service.dto.request.survey.SurveyRequestDto;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.attendance.AttendanceMapper;
import com.security.Jwt_service.repository.AttendanceRepository;
import com.security.Jwt_service.repository.SessionRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.impl.SurveyServiceImpl;
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
class SurveyServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private AttendanceMapper attendanceMapper;
    @InjectMocks
    private SurveyServiceImpl surveyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addSurveyInfo_shouldThrowException_whenStudentNotFound() {
        when(studentRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
        SurveyRequestDto dto = new SurveyRequestDto();
        assertThrows(ResourceNotFoundException.class, () -> surveyService.addSurveyInfo(dto, 1L));
    }
} 