package com.security.Jwt_service.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDto {
    private Long id;
    private String username;
    private String name;
    private LocalDate dob;
    private String phoneNumber;
    private String roleCode;
}
