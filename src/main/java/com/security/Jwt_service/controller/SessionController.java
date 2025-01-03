package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.request.session.SessionUpdateDto;
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

import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@Tag(name = "Session Controller")
public class SessionController {
    private final SessionService sessionService;
    @Operation(summary = "Add batch of sessions (the first session is start according to the start time of its class", description = "API for add batch session")
    @PostMapping("/{frequency}")
    public ResponseEntity<SessionResponseCreateDto> createSessions(@RequestBody SessionCreateDto dto, @PathVariable(name = "frequency") int frequency){
        return new ResponseEntity<>(sessionService.createSessions(dto,frequency), HttpStatus.CREATED);
    }
    @Operation(summary = "Delete session", description = "API for delete session")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable(name = "id") Long id){
        sessionService.deleteSession(id);
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    }
    @Operation(summary = "Update sessions", description = "API for update session")
    @PutMapping("/update")
    public ResponseEntity<List<SessionResponseDto>> updateSession(@RequestBody SessionUpdateDto dto){
        return new ResponseEntity<>(sessionService.updateSession(dto), HttpStatus.OK);
    }
    @Operation(summary = "Change rep for a sessions", description = "API for change rep session")
    @PutMapping("/{sessionId}/{studentId}")
    public ResponseEntity<SessionResponseDto> updateSession(@PathVariable(name = "sessionId") Long sessionId,
                                                                  @PathVariable(name = "studentId") Long studentId){
        return new ResponseEntity<>(sessionService.changeRep(sessionId,studentId), HttpStatus.OK);
    }
}
