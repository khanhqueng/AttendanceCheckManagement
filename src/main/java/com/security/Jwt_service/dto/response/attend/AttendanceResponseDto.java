package com.security.Jwt_service.dto.response.attend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.dto.response.session.SessionResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AttendanceResponseDto {
    private Long id;
    private String status;

    private LocalDateTime onClassTime;

    private StudentResponseDto student;

    private SessionResponseDto session;
}
