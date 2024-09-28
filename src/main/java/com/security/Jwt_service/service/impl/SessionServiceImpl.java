package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseDto;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.mapper.session.SessionMapper;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.repository.SessionRepository;
import com.security.Jwt_service.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final ClassroomRepository classroomRepository;

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
            session.setNo(i);
            sessions.add(sessionMapper.entityToResponse( sessionRepository.save(session)));
        }
        responseDto.setSessions(sessions);
        return responseDto;
    }
}
