package com.security.Jwt_service.util;

import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.repository.SessionRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class MapperUtils {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ClassroomRepository classroomRepository;

    public Classroom mapToClassroom(Long id){
        return classroomRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Classroom", "id", id)
        );
    }
}
