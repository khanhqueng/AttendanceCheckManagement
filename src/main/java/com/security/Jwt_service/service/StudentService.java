package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.student.SearchAttendanceHistoryDto;
import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.student.StudentAttendanceHistoryResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.dto.response.student.StudentWithViolation;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.service.factorymethod.UserCreateMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StudentService {
//    StudentResponseDto createStudent(StudentCreateDto createDto);
    List<StudentResponseDto> addStudentThroughExcel(MultipartFile excelFile) throws IOException;
    List<StudentResponseDto> getAllStudent();
    StudentAttendanceHistoryResponseDto searchStudent(String studentCode, LocalDate startDate, LocalDate endDate);
//    String getAllStudentAdvance();
    List<StudentWithViolation> getAllStudentWithViolation(Long classroomId);
}
