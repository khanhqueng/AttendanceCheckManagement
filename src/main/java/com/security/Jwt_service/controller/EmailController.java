package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.email.SendBatchDto;
import com.security.Jwt_service.dto.request.email.StudentSenderDto;
import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;
import com.security.Jwt_service.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Tag(name = "Email Controller")
public class EmailController {
    private final EmailService emailService;
    @Operation(summary = "Send email to selected student", description = "API for send email to selected student")
    @PostMapping("/{classroomId}")
    public ResponseEntity<String> sendBatchStudent(@RequestBody SendBatchDto dto, @PathVariable(name = "classroomId") Long classroomId){
        emailService.sendAlertToMultipleStudent(classroomId,dto);
        return new ResponseEntity<>("Sent success", HttpStatus.OK);
    }
}
