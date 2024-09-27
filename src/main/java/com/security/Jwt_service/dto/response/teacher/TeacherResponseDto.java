package com.security.Jwt_service.dto.response.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
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

public class TeacherResponseDto {
    private Long id;

    private String email;

    private String name;

    private String phoneNumber;

    private String teacherCode;

    private LocalDate date;
}
