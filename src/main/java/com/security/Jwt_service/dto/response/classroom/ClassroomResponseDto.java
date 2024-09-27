package com.security.Jwt_service.dto.response.classroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;
import com.security.Jwt_service.entity.user.Student;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClassroomResponseDto {
    private String name;

    private LocalDate beginDate;

    private LocalDate endDate;

    private int allowedLateTime;

    private CourseResponseDto course;

    private TeacherResponseDto teacher;

    private Set<StudentResponseDto> students;
}
