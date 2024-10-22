package com.security.Jwt_service.mapper.student;

import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.dto.response.user.UserResponseFactory;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StudentMapper extends GenericMapper<Student, UserCreateDto, StudentResponseDto> {

    @Override
    @Mapping(target = "id",source = "entity.id")
    StudentResponseDto entityToResponse(Student entity);

    @Override
    @Mapping(target = "studentCode", source = "request.roleCode")
    Student requestToEntity(UserCreateDto request);

    @Mapping(target = "roleCode", source = "student.studentCode")
    @Mapping(target = "id",source = "student.id")
    UserResponseFactory entityToResponseFactory(Student student);
}
