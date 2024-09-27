package com.security.Jwt_service.dto.request.course;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CourseCreateDto {
    @NotNull(message = "name of course must not be null")
    private String name;
    @NotNull(message = "code of course must not be null")
    private String courseCode;
}
