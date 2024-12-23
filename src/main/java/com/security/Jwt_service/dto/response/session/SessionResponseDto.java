package com.security.Jwt_service.dto.response.session;

import com.fasterxml.jackson.annotation.JsonFormat;
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

public class SessionResponseDto {
    private Long id;
    private int no;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
