package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@Tag(name = "Session Controller")
public class SessionController {
    private final SessionService sessionService;
    @Operation(summary = "Add batch of sessions", description = "API for create batch of sessions")
    @PostMapping("/{frequency}")
    public ResponseEntity<SessionResponseCreateDto> createSessions(@RequestBody SessionCreateDto dto, @PathVariable(name = "frequency") int frequency){
        return new ResponseEntity<>(sessionService.createSessions(dto,frequency), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable(name = "id") Long id){
        sessionService.deleteSession(id);
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    }
}
