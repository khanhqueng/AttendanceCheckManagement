package com.security.Jwt_service.dto.response.classroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;
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

public class ClassroomWithMostAbsentStudent {
    private Long id;
    private String name;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    private TeacherResponseDto teacher;
    private Integer numAbsentStudent;
}
