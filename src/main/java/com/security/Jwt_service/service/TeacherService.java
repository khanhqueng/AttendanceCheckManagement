package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.teacher.TeacherCreateDto;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;

public interface TeacherService {
    TeacherResponseDto createTeacher(TeacherCreateDto createDto);
}
