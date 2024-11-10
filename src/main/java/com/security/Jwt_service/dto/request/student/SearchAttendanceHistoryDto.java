package com.security.Jwt_service.dto.request.student;

import com.security.Jwt_service.validation.DateCheck;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DateCheck(startDate = "startDate",endDate = "endDate")
public class SearchAttendanceHistoryDto {
    @NotNull(message = "student code must not be null")
    private String studentCode;
    @NotNull(message = "start date must not be null")
    private LocalDate startDate;
    @NotNull(message = "end date must not be null")
    private LocalDate endDate;
}
