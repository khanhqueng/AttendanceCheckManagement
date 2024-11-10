package com.security.Jwt_service.dto.response.attend;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistoryResponseDto {
    private int no;
    private String status;
    // gio len lop
    private LocalDateTime onClassTime;
    private LocalDateTime startTime;
    private String className;
}
