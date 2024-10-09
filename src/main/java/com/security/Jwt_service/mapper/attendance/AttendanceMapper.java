package com.security.Jwt_service.mapper.attendance;

import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.mapper.GenericMapper;
import com.security.Jwt_service.mapper.session.SessionMapper;
import com.security.Jwt_service.mapper.student.StudentMapper;
import org.mapstruct.Mapper;

@Mapper(uses = {StudentMapper.class, SessionMapper.class})
public interface AttendanceMapper extends GenericMapper<Attendance, Attendance, AttendanceResponseDto> {
}