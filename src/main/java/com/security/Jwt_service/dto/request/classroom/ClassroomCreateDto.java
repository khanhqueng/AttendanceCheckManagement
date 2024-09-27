package com.security.Jwt_service.dto.request.classroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClassroomCreateDto {
    @NotNull(message = "name of classroom must not be null")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "begin date of classroom must not be null")
    private LocalDate beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "end date of classroom must not be null")
    private LocalDate endDate;

    private int allowedLateTime;
}
