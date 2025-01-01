package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.request.session.SessionUpdateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseDto;

import java.util.List;

public interface SessionService {
    SessionResponseCreateDto createSessions(SessionCreateDto createDto, int frequency);
    List<SessionResponseDto> updateSession(SessionUpdateDto updateDto);
    void deleteSession(Long sessionId);
}
