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
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    @Override
    public AttendanceResponseDto attendStudent(Long sessionId, Long userId, LocalTime startTime, LocalTime endTime) {
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
        // get current time according to Viet nam timeline
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        attendance.setOnClassTime(LocalDateTime.now(zoneId));
        LocalTime now = LocalDateTime.now(zoneId).toLocalTime();
        // check late or on time
        if(now.isAfter(startTime) && now.isBefore(endTime)){
            attendance.setStatus("Dung gio");
            return attendanceMapper.entityToResponse(attendanceRepository.save(attendance));
        }
        else if (now.isAfter(endTime)){
            Duration duration= Duration.between(endTime,now);
            attendance.setStatus(String.format("Di tre %s phut", duration.toMinutes()));
            return attendanceMapper.entityToResponse(attendanceRepository.save(attendance));
        }
        return null;
    }

    @Override
    public AttendanceResponseDto updateAbsentStudent(Long sessionId, Long userId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(
                ()-> new ResourceNotFoundException("Session", "id", sessionId)
        );
        Student student = studentRepository.findById(userId).orElseThrow(
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

    @Override
    public List<AttendanceResponseDto> getAllAttendanceByStudentId(Long userId) {
        Student student= studentRepository.findByUserId(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User","id", userId )
        );
        return student.getAttendances().stream().map(attendanceMapper::entityToResponse).toList();
    }

    @Override
    public List<AttendanceResponseDto> getAttendanceBySessionId(Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(
                ()-> new ResourceNotFoundException("Session", "id", sessionId)
        );
        return session.getAttendances().stream().map(attendanceMapper::entityToResponse).toList();
    }

    @Override
    public void deleteALlAttendance() {
        attendanceRepository.deleteAll();
    }
}
