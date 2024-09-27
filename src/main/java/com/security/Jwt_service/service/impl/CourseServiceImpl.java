package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.course.CourseCreateDto;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.entity.course.Course;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.mapper.course.CourseMapper;
import com.security.Jwt_service.repository.CourseRepository;
import com.security.Jwt_service.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    @Override
    public CourseResponseDto createCourse(CourseCreateDto createDto) {
        if(courseRepository.existsByNameOrCourseCode(createDto.getName(), createDto.getCourseCode())){
            throw new ResourceDuplicateException("Course", "name or course code", createDto.getName() + " "+ createDto.getCourseCode());
        }
        Course course = courseMapper.requestToEntity(createDto);
        return courseMapper.entityToResponse(courseRepository.save(course));
    }
}
