package com.security.Jwt_service.dto.request.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentSenderDto {
    private String typeViolation;
    private String studentCode;
    private Integer numberViolations;
    private Integer maximumViolationAllowed;

}
