package com.security.Jwt_service.mapper.classroom;

import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomForRollCaller;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomStudentIn;
import com.security.Jwt_service.dto.response.classroom.ClassroomWithMostAbsentStudent;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.entity.course.Course;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.mapper.GenericMapper;
import com.security.Jwt_service.mapper.course.CourseMapper;
import com.security.Jwt_service.mapper.session.SessionMapper;
import com.security.Jwt_service.mapper.student.StudentMapper;
import com.security.Jwt_service.mapper.teacher.TeacherMapper;
import com.security.Jwt_service.util.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {TeacherMapper.class, StudentMapper.class, CourseMapper.class, SessionMapper.class, MapperUtils.class})
public interface ClassroomMapper extends GenericMapper<Classroom, ClassroomCreateDto, ClassroomResponseDto> {

    @Override
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "classMonitor", source = "entity.classRepId")
    ClassroomResponseDto entityToResponse(Classroom entity);

    @Mapping(target = "id", source = "entity.id")
    ClassroomForRollCaller fromEntityToClassForRollCall(Classroom entity);
    @Mapping(target = "id", source = "entity.id")
    ClassroomWithMostAbsentStudent fromEntityToClassWithMostAbsent(Classroom entity);

    @Mapping(target = "id", source = "entity.id")
    ClassroomStudentIn fromEntityToClassOfAStudentStudying(Classroom entity);

}
