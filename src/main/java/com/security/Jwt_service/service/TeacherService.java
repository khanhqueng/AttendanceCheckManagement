package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.teacher.TeacherCreateDto;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;
import com.security.Jwt_service.service.factorymethod.UserCreateMethod;

import java.util.List;

public interface TeacherService {
//    TeacherResponseDto createTeacher(TeacherCreateDto createDto);
    List<TeacherResponseDto> getAllTeachers();
}
