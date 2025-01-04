package com.security.Jwt_service.dto.request.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendBatchDto {
    private String opinion;
    private List<StudentSenderDto> studentList;
}
