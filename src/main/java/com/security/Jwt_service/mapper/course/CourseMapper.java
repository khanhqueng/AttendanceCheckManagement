package com.security.Jwt_service.mapper.course;

import com.security.Jwt_service.dto.request.course.CourseCreateDto;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.entity.course.Course;
import com.security.Jwt_service.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper
public interface CourseMapper extends GenericMapper<Course, CourseCreateDto, CourseResponseDto> {
}
