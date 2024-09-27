package com.security.Jwt_service.mapper.teacher;

import com.security.Jwt_service.dto.request.teacher.TeacherCreateDto;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;
import com.security.Jwt_service.entity.user.Teacher;
import com.security.Jwt_service.mapper.GenericMapper;
import com.security.Jwt_service.mapper.student.StudentMapper;
import org.mapstruct.Mapper;

@Mapper
public interface TeacherMapper extends GenericMapper<Teacher, TeacherCreateDto, TeacherResponseDto> {
}
