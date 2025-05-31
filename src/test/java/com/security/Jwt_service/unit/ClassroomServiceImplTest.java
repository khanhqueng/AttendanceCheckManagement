package com.security.Jwt_service.unit;

import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.classroom.ClassroomMapper;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.service.impl.ClassroomServiceImpl;
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
class ClassroomServiceImplTest {
    @Mock
    private ClassroomRepository classroomRepository;
    @Mock
    private ClassroomMapper classroomMapper;
    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getClassroomById_shouldReturnClassroomResponseDto_whenFound() {
        Classroom classroom = new Classroom();
        ClassroomResponseDto responseDto = new ClassroomResponseDto();
        when(classroomRepository.findById(1L)).thenReturn(Optional.of(classroom));
        when(classroomMapper.entityToResponse(classroom)).thenReturn(responseDto);
        ClassroomResponseDto result = classroomService.getClassroomById(1L);
        assertEquals(responseDto, result);
    }

    @Test
    void getClassroomById_shouldThrowException_whenNotFound() {
        when(classroomRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> classroomService.getClassroomById(1L));
    }
} 