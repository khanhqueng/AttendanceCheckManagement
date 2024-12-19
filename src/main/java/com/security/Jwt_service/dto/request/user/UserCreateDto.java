package com.security.Jwt_service.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.util.RoleName;
import com.security.Jwt_service.validation.EnumCheck;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "22520644@gm.uit.edu.vn")
    private String email;

    @NotNull(message = "role name must not be null")
    @EnumCheck(enumClass = RoleName.class, message = "Role name must be Student or Teacher")
    @Schema(description = "role of user (Student or Teacher)", example = "Student")
    private String roleName;

    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "phone number must not be null")
    private String phoneNumber;

    @NotNull(message = "role code must not be null")
    @Schema(description = "code of user", example = "22520648")
    private String roleCode;

    @NotNull(message = "date of birth must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "birthday of user", example = "2004-08-23")
    private LocalDate date;
}
