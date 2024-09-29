package com.security.Jwt_service.controller;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.request.course.CourseCreateDto;
import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    @PostMapping("/{session_id}")
    public ResponseEntity<AttendanceResponseDto> createCourse(Authentication authentication, @PathVariable(name = "session_id") Long id){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(attendanceService.attendStudent(id,userId), HttpStatus.CREATED);
    }
    @PostMapping("/{session_id}/{student_id}")
    public ResponseEntity<AttendanceResponseDto> createCourse(@PathVariable(name = "session_id") Long sessionId,
                                                              @PathVariable(name = "student_id") Long studentId){
        return new ResponseEntity<>(attendanceService.updateAbsentStudent(sessionId,studentId), HttpStatus.OK);
    }
}
