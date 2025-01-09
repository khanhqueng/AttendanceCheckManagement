package com.security.Jwt_service.dto.request.attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRangeDto {
    private LocalTime startTime;
    private LocalTime endTime;
}
