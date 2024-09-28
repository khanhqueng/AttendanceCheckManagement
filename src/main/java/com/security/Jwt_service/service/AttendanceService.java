package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;

public interface AttendanceService {
    AttendanceResponseDto attendStudent(Long sessionId, Long userId);
}
