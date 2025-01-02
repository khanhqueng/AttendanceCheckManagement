package com.security.Jwt_service.dto.request.classroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.validation.DateCheck;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@DateCheck(startDate = "beginDate", endDate = "endDate")
public class ClassroomCreateDto {
    @NotNull(message = "name of classroom must not be null")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "begin date of classroom must not be null")
    private LocalDate beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "end date of classroom must not be null")
    private LocalDate endDate;

    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "start time of classroom must not be null")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "end time of classroom must not be null")
    private LocalTime endTime;

    private Integer allowedLateTime;
    private Long teacherId;
    private Long courseId;
}
