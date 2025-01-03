package com.security.Jwt_service.util;

import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.repository.SessionRepository;
import com.security.Jwt_service.repository.StudentRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class MapperUtils {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private StudentRepository studentRepository;

    public Classroom mapToClassroom(Long id){
        return classroomRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Classroom", "id", id)
        );
    }
    public ClassroomResponseDto.ClassMonitor mapToMonitor(Long id){
        if(id==null) return null;
        Student student= studentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Student", "id", id)
        );
        return new ClassroomResponseDto.ClassMonitor(student.getStudentCode(), student.getName());
    }

}
