package com.security.Jwt_service.mapper.classroom;

import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomForRollCaller;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomWithMostAbsentStudent;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.entity.course.Course;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.mapper.GenericMapper;
import com.security.Jwt_service.mapper.course.CourseMapper;
import com.security.Jwt_service.mapper.session.SessionMapper;
import com.security.Jwt_service.mapper.student.StudentMapper;
import com.security.Jwt_service.mapper.teacher.TeacherMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {TeacherMapper.class, StudentMapper.class, CourseMapper.class, SessionMapper.class})
public interface ClassroomMapper extends GenericMapper<Classroom, ClassroomCreateDto, ClassroomResponseDto> {

    @Mapping(target = "id", source = "entity.id")
    ClassroomForRollCaller fromEntityToClassForRollCall(Classroom entity);
    @Mapping(target = "id", source = "entity.id")
    ClassroomWithMostAbsentStudent fromEntityToClassWithMostAbsent(Classroom entity);
}
