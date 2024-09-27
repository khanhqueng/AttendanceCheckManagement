package com.security.Jwt_service.dto.response.course;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CourseResponseDto {
    private Long id;

    private String name;

    private String courseCode;
}
