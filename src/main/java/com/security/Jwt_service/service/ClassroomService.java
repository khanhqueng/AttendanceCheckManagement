package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;

import java.util.List;

public interface ClassroomService {
    ClassroomResponseDto createClassroom(ClassroomCreateDto createDto, List<Long> studentIds, Long teacherId, Long courseId);
    List<ClassroomResponseDto> getAllClassrooms();
    ClassroomResponseDto getClassroomById(Long id);
}
