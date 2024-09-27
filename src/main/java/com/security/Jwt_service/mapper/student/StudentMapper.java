package com.security.Jwt_service.mapper.student;

import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StudentMapper extends GenericMapper<Student, StudentCreateDto, StudentResponseDto> {

    @Override
    @Mapping(target = "id",source = "entity.id")
    StudentResponseDto entityToResponse(Student entity);
}
