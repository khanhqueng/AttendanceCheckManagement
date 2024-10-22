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
public class UserResponseFactory {
    private Long id;

    private String email;

    private String name;

    private String phoneNumber;

    private String roleCode;

    private LocalDate date;
}
