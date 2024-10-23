package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;

public interface SessionService {
    SessionResponseCreateDto createSessions(SessionCreateDto createDto, int frequency);
    void deleteSession(Long sessionId);
}
