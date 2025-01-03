package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.survey.SurveyRequestDto;
import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;

public interface SurveyService {
    AttendanceResponseDto addSurveyInfo(SurveyRequestDto dto, Long userId);
}
