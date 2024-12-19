package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.request.teacher.TeacherCreateDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;
import com.security.Jwt_service.service.StudentService;
import com.security.Jwt_service.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
@Tag(name = "Teacher Controller")
public class TeacherController {
    private final TeacherService teacherService;
    @Operation(summary = "Get all teacher", description = "API for get all teacher")
    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeacher() throws IOException {
        return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.OK);
    }

}
