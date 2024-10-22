package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.attendance.AttendanceMapper;
import com.security.Jwt_service.repository.AttendanceRepository;
import com.security.Jwt_service.repository.SessionRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    @Override
    public AttendanceResponseDto attendStudent(Long sessionId, Long userId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(
                ()-> new ResourceNotFoundException("Session", "id", sessionId)
        );
        Student student = studentRepository.findByUserId(userId).orElseThrow(
                ()-> new ResourceNotFoundException("Student", "id", userId)
        );
        if(attendanceRepository.existsByStudentIdAndSessionId(student.getId(), session.getId()))
            throw new AppApiException(HttpStatus.BAD_REQUEST, "You have attended this session");
        Attendance attendance= new Attendance();
        attendance.setStudent(student);
        attendance.setSession(session);
        // get on-class time of the class
        LocalTime onClassTime= session.getStartTime().toLocalTime();
        // get current time according to Viet nam timeline
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        attendance.setOnClassTime(LocalDateTime.now(zoneId));
        LocalTime now = LocalDateTime.now(zoneId).toLocalTime();
        // check late or on time
        if(onClassTime.isAfter(now)){
            attendance.setStatus("Dung gio");
            return attendanceMapper.entityToResponse(attendanceRepository.save(attendance));
        }
        else{
            Duration duration= Duration.between(onClassTime,now);
            attendance.setStatus(String.format("Di tre %s phut", duration.toMinutes()));
            return attendanceMapper.entityToResponse(attendanceRepository.save(attendance));
        }
    }

    @Override
    public AttendanceResponseDto updateAbsentStudent(Long sessionId, Long userId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(
                ()-> new ResourceNotFoundException("Session", "id", sessionId)
        );
        Student student = studentRepository.findByUserId(userId).orElseThrow(
                ()-> new ResourceNotFoundException("Student", "id", userId)
        );
        if(attendanceRepository.existsByStudentIdAndSessionId(student.getId(), session.getId()))
            throw new AppApiException(HttpStatus.BAD_REQUEST, "You have attended this session");
        Attendance attendance= new Attendance();
        attendance.setStudent(student);
        attendance.setSession(session);
        attendance.setStatus("Vang co phep");
        return attendanceMapper.entityToResponse(attendanceRepository.save(attendance));
    }
}
