package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.entity.user.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    StudentResponseDto createStudent(StudentCreateDto createDto);
    List<StudentResponseDto> addStudentThroughExcel(MultipartFile excelFile) throws IOException;
    List<StudentResponseDto> getAllStudent();
}
