package com.security.Jwt_service.dto.response.classroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.dto.response.session.SessionResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.dto.response.teacher.TeacherResponseDto;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClassroomResponseDto {
    private Long id;
    private String name;

    private LocalDate beginDate;

    private LocalDate endDate;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    private Integer allowedLateTime;
    private Integer allowedAbsent;

    private ClassMonitor classMonitor;

    private CourseResponseDto course;

    private TeacherResponseDto teacher;

    private Set<StudentResponseDto> students;
    private Set<SessionResponseDto> sessions;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassMonitor {
        private String studentCode;
        private String name;
    }
}
