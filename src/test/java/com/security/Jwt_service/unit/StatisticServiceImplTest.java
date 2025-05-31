package com.security.Jwt_service.unit;

import com.security.Jwt_service.dto.response.statistic.StatisticForManager;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.mapper.classroom.ClassroomMapper;
import com.security.Jwt_service.repository.AttendanceRepository;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.service.impl.StatisticServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class StatisticServiceImplTest {
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private ClassroomMapper classroomMapper;
    @Mock
    private ClassroomRepository classroomRepository;
    @InjectMocks
    private StatisticServiceImpl statisticService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void statisticForManager_shouldThrowException_whenInvalidTimeDigit() {
        assertThrows(AppApiException.class, () -> statisticService.statisticForManager("Invalid"));
    }
} 