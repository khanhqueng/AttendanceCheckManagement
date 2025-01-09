package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceService {
    AttendanceResponseDto attendStudent(Long sessionId, Long userId,LocalTime startTime, LocalTime endTime);
    AttendanceResponseDto updateAbsentStudent(Long sessionId, Long userId);
    List<AttendanceResponseDto> getAllAttendanceByStudentId(Long studentId);
    List<AttendanceResponseDto> getAttendanceBySessionId(Long sessionId);
    void deleteALlAttendance();
}
