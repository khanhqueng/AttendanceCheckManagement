package com.security.Jwt_service.dto.response.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithViolation {
    private Long id;
    private String name;
    private String studentCode;
    private Integer absentWithoutPermission;
    private Integer late;
    private Integer absentWithPermission;
}
