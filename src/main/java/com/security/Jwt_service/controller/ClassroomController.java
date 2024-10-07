package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classroom")
@RequiredArgsConstructor
@Tag(name = "Classroom Controller")
public class ClassroomController {
    private final ClassroomService classroomService;
    @Operation(summary = "Create classroom", description = "API for create new classroom")
    @PostMapping
    public ResponseEntity<ClassroomResponseDto> createClassroom(@RequestBody ClassroomCreateDto dto,
                                                                @RequestParam(name = "student") List<Long> studentId,
                                                                @RequestParam(name = "teacher") Long teacherId,
                                                                @RequestParam(name = "course") Long courseId){
        return new ResponseEntity<>(classroomService.createClassroom(dto, studentId, teacherId, courseId), HttpStatus.CREATED);
    }
}
