package com.security.Jwt_service.unit;

import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.attendance.AttendanceMapper;
import com.security.Jwt_service.repository.AttendanceRepository;
import com.security.Jwt_service.repository.SessionRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.impl.AttendanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class AttendanceServiceImplTest {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private AttendanceMapper attendanceMapper;
    private Clock clock;
    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clock = Clock.fixed(Instant.parse("2024-07-01T01:30:00Z"), ZoneId.of("Asia/Ho_Chi_Minh"));
        attendanceService = new AttendanceServiceImpl(sessionRepository, studentRepository, attendanceRepository, attendanceMapper, clock);
    }

    @Test
    void attendStudent_shouldReturnResponse_whenOnTime() {
        Long sessionId = 1L;
        Long userId = 2L;
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        Session session = new Session();
        Student student = new Student();
        Attendance attendance = new Attendance();
        AttendanceResponseDto responseDto = new AttendanceResponseDto();

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(studentRepository.findByUserId(userId)).thenReturn(Optional.of(student));
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        when(attendanceMapper.entityToResponse(any(Attendance.class))).thenReturn(responseDto);

        AttendanceResponseDto result = attendanceService.attendStudent(sessionId, userId, startTime, endTime);
        assertNotNull(result);
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    void attendStudent_shouldReturnResponse_whenLate() {
        Long sessionId = 1L;
        Long userId = 2L;
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(8, 15);
        Session session = new Session();
        Student student = new Student();
        Attendance attendance = new Attendance();
        AttendanceResponseDto responseDto = new AttendanceResponseDto();

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(studentRepository.findByUserId(userId)).thenReturn(Optional.of(student));
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        when(attendanceMapper.entityToResponse(any(Attendance.class))).thenReturn(responseDto);

        AttendanceResponseDto result = attendanceService.attendStudent(sessionId, userId, startTime, endTime);
        assertNotNull(result);
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    void updateAbsentStudent_shouldReturnResponse_whenValid() {
        Long sessionId = 1L;
        Long userId = 2L;
        Session session = new Session();
        session.setId(sessionId);
        Student student = new Student();
        student.setId(userId);
        Attendance attendance = new Attendance();
        AttendanceResponseDto responseDto = new AttendanceResponseDto();

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(studentRepository.findById(userId)).thenReturn(Optional.of(student));
        when(attendanceRepository.existsByStudentIdAndSessionId(userId, sessionId)).thenReturn(false);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        when(attendanceMapper.entityToResponse(any(Attendance.class))).thenReturn(responseDto);

        AttendanceResponseDto result = attendanceService.updateAbsentStudent(sessionId, userId);
        assertNotNull(result);
        verify(attendanceRepository, times(1)).save(any(Attendance.class));
    }

    @Test
    void updateAbsentStudent_shouldThrowException_whenAlreadyAttended() {
        Long sessionId = 1L;
        Long userId = 2L;
        Session session = new Session();
        session.setId(sessionId);
        Student student = new Student();
        student.setId(userId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(studentRepository.findById(userId)).thenReturn(Optional.of(student));
        when(attendanceRepository.existsByStudentIdAndSessionId(userId, sessionId)).thenReturn(true);

        AppApiException exception = assertThrows(AppApiException.class, () ->
            attendanceService.updateAbsentStudent(sessionId, userId)
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void updateAbsentStudent_shouldThrowException_whenSessionNotFound() {
        Long sessionId = 1L;
        Long userId = 2L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
            attendanceService.updateAbsentStudent(sessionId, userId)
        );
        assertEquals("Session", exception.getResourceName());
    }

    @Test
    void updateAbsentStudent_shouldThrowException_whenStudentNotFound() {
        Long sessionId = 1L;
        Long userId = 2L;
        Session session = new Session();
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(studentRepository.findById(userId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
            attendanceService.updateAbsentStudent(sessionId, userId)
        );
        assertEquals("Student", exception.getResourceName());
    }
}
