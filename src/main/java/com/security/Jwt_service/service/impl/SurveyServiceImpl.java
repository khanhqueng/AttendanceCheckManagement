package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.survey.SurveyRequestDto;
import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.attendance.AttendanceMapper;
import com.security.Jwt_service.repository.AttendanceRepository;
import com.security.Jwt_service.repository.SessionRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final StudentRepository studentRepository;
    private final SessionRepository sessionRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    @Override
    public AttendanceResponseDto addSurveyInfo(SurveyRequestDto dto, Long userId) {
        Student student= studentRepository.findByUserId(userId).orElseThrow(
                ()-> new ResourceNotFoundException("Student", "user id", userId)
        );
        Session session = sessionRepository.findById(dto.getSessionId()).orElseThrow(
                ()-> new ResourceNotFoundException("Session", "id", dto.getSessionId())
        );
        Attendance attendance = attendanceRepository.findByStudentIdAndSessionId(student.getId(),session.getId()).orElseThrow(
                ()-> new AppApiException(HttpStatus.BAD_REQUEST,"You must attend before having permission to submit this form")
        );
        attendance.setEfficientRate(dto.getEfficientRate());
        attendance.setUnderStandingRate(dto.getUnderStandingRate());
        return attendanceMapper.entityToResponse(attendanceRepository.save(attendance));
    }
}
