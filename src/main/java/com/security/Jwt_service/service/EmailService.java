package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.email.StudentSenderDto;

import java.util.List;

public interface EmailService {
    void sendAlertToMultipleStudent(Long classroomId, List<StudentSenderDto> dto);
}