package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    @PostMapping("/{frequency}")
    public ResponseEntity<SessionResponseCreateDto> createSessions(@RequestBody SessionCreateDto dto, @PathVariable(name = "frequency") int frequency){
        return new ResponseEntity<>(sessionService.createSessions(dto,frequency), HttpStatus.CREATED);
    }
}
