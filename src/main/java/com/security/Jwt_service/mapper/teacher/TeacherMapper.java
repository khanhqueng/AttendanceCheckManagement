package com.security.Jwt_service.mapper.teacher;

import com.security.Jwt_service.dto.request.teacher.TeacherCreateDto;
import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;
import com.security.Jwt_service.dto.response.user.UserResponseFactory;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.Teacher;
import com.security.Jwt_service.mapper.GenericMapper;
import com.security.Jwt_service.mapper.student.StudentMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TeacherMapper extends GenericMapper<Teacher, UserCreateDto, TeacherResponseDto> {
    @Override
    @Mapping(target = "teacherCode", source = "request.roleCode")
    Teacher requestToEntity(UserCreateDto request);

    @Override
    @Mapping(target = "id",source = "entity.id")
    TeacherResponseDto entityToResponse(Teacher entity);

    @Mapping(target = "roleCode", source = "teacher.teacherCode")
    @Mapping(target = "id",source = "teacher.id")
    UserResponseFactory entityToResponseFactory(Teacher teacher);
}
