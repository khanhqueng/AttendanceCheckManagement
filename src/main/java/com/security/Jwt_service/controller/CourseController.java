package com.security.Jwt_service.controller;


import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.request.course.CourseCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Tag(name = "Course Controller")
public class CourseController {
    private final CourseService courseService;
    @Operation(summary = "Add course", description = "API for create new course")
    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseCreateDto dto){
        return new ResponseEntity<>(courseService.createCourse(dto), HttpStatus.CREATED);
    }
}
