package com.security.Jwt_service.dto.request.user;

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
@AllArgsConstructor
@NoArgsConstructor

public class UserCreateDto {
    @Email
    @NotNull(message = "email must not be null")
    private String email;

    @NotNull(message = "role name must not be null")
    private String roleName;

    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "phone number must not be null")
    private String phoneNumber;

    @NotNull(message = "role code must not be null")
    private String roleCode;

    @NotNull(message = "date of birth must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
