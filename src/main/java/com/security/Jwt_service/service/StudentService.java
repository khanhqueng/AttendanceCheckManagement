package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;

public interface StudentService {
    StudentResponseDto createStudent(StudentCreateDto createDto);
}
