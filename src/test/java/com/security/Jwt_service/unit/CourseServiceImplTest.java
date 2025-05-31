package com.security.Jwt_service.unit;

import com.security.Jwt_service.dto.request.course.CourseCreateDto;
import com.security.Jwt_service.dto.request.course.CourseUpdateDto;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.entity.course.Course;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.course.CourseMapper;
import com.security.Jwt_service.repository.CourseRepository;
import com.security.Jwt_service.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseMapper courseMapper;
    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCourse_shouldThrowException_whenDuplicate() {
        CourseCreateDto dto = new CourseCreateDto();
        when(courseRepository.existsByNameOrCourseCode(any(), any())).thenReturn(true);
        assertThrows(ResourceDuplicateException.class, () -> courseService.createCourse(dto));
    }

    @Test
    void updateCourse_shouldThrowException_whenNotFound() {
        CourseUpdateDto dto = new CourseUpdateDto();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> courseService.updateCourse(1L, dto));
    }
} 