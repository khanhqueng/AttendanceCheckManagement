package com.security.Jwt_service.controller;

import com.security.Jwt_service.dto.request.student.SearchAttendanceHistoryDto;
import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.attend.SearchHistoryResponseDto;
import com.security.Jwt_service.dto.response.student.StudentAttendanceHistoryResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Tag(name = "Student Controller")
public class StudentController {
    private final StudentService studentService;
    @PostMapping(value = "/import",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<StudentResponseDto>> importStudentThroughExcel(@RequestBody MultipartFile excelFile) throws IOException {
        return new ResponseEntity<>(studentService.addStudentThroughExcel(excelFile), HttpStatus.CREATED);
    }
    @SecurityRequirement(name = "Authorization")
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudent() throws IOException {
        return new ResponseEntity<>(studentService.getAllStudent(), HttpStatus.OK);
    }
    @GetMapping("/redis")
    public ResponseEntity<String> getAllStudentAdvance() throws IOException {
        return new ResponseEntity<>(studentService.getAllStudentAdvance(), HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<StudentAttendanceHistoryResponseDto> searchStudent(@Valid @RequestBody SearchAttendanceHistoryDto dto) {
        return new ResponseEntity<>(studentService.searchStudent(dto), HttpStatus.OK);
    }
}
