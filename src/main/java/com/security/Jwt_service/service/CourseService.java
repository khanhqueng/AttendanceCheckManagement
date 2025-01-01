package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.course.CourseCreateDto;
import com.security.Jwt_service.dto.request.course.CourseUpdateDto;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;

import java.util.List;

public interface CourseService {
    CourseResponseDto createCourse(CourseCreateDto createDto);
    List<CourseResponseDto> getAllCourses();
    CourseResponseDto updateCourse(Long courseId, CourseUpdateDto updateDto);
}
