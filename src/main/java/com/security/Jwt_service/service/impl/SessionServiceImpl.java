package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.request.session.SessionUpdateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseDto;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.session.SessionMapper;
import com.security.Jwt_service.repository.AttendanceRepository;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.repository.SessionRepository;
import com.security.Jwt_service.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class SessionServiceImpl implements SessionService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final ClassroomRepository classroomRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    public SessionResponseCreateDto createSessions(SessionCreateDto createDto, int frequency) {
        if(sessionRepository.existsByClassroomId(createDto.getClassRoomId()))
            throw new ResourceDuplicateException("Classroom", "id", createDto.getClassRoomId());
        SessionResponseCreateDto responseDto= new SessionResponseCreateDto();
        Set<SessionResponseDto> sessions = new HashSet<>();
        for(int i=1;i<= createDto.getNumberSessions(); i++){
            Session session= sessionMapper.requestToEntity(createDto);
            session.setStartTime(
                    session.getClassroom().getBeginDate().atTime(
                    createDto.getOnClassTime().getHour(),
                    createDto.getOnClassTime().getMinute()
            ).plusDays((long) (i - 1) * frequency));
            session.setEndTime(
                    session.getClassroom().getBeginDate().atTime(
                            createDto.getEndClassTime().getHour(),
                            createDto.getEndClassTime().getMinute()
                    ).plusDays((long) (i - 1) * frequency));
            session.setNo(i);
            session.setIsOver(0);
            sessions.add(sessionMapper.entityToResponse( sessionRepository.save(session)));
        }
        responseDto.setSessions(sessions);
        return responseDto;
    }

    @Override
    public List<SessionResponseDto> updateSession(SessionUpdateDto updateDto) {
        List<Long> sessionIds= updateDto.getSessionIds();
        List<Session> sessions = sessionRepository.findAllById(sessionIds);
        sessions.forEach(session -> session.setStartTime(updateDto.getStartTime()));
        return sessionRepository.saveAll(sessions).stream().map(sessionMapper::entityToResponse).toList();
    }

    @Override
    public void deleteSession(Long sessionId) {
        Session session= sessionRepository.findById(sessionId).orElseThrow(
                ()-> new ResourceNotFoundException("Session", "id", sessionId)
        );
        sessionRepository.delete(session);
    }

    @Scheduled(fixedRate = 300000)
    public void updateAbsentStudent(){
        try {
            List<Session> sessions= sessionRepository.findAllByIsOver(0).orElseThrow(
                    ()-> new ResourceNotFoundException("Session", "status", "is not over")
            );
            ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
            LocalDateTime now = LocalDateTime.now(zoneId);
            for(Session session : sessions){

                if(session.getEndTime().isBefore(now)){
                    session.setIsOver(1);

                    Set<Student> allStudentsAtSession= session.getClassroom().getStudents();
                    Set<Student> attendances = session.getAttendances().stream().map(Attendance::getStudent).collect(Collectors.toSet());
                    for(Student student: allStudentsAtSession){
                        if(!attendances.contains(student)){
                            Attendance missingAttendance= Attendance.builder()
                                    .student(student)
                                    .status("Vang ko phep")
                                    .session(session)
                            .build();
                            attendanceRepository.save(missingAttendance);
                        }
                    }
                    sessionRepository.save(session);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
