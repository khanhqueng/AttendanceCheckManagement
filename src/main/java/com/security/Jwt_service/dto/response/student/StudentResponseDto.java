package com.security.Jwt_service.dto.response.student;

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

public class StudentResponseDto {
    private Long id;

    private String email;

    private String name;

    private String phoneNumber;

    private String studentCode;

    private LocalDate date;
}
