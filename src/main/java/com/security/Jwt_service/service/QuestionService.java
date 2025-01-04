package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.request.question.AddQuestionDto;
import com.security.Jwt_service.dto.response.question.QuestionResponseDto;

import java.util.List;

public interface QuestionService {
    QuestionResponseDto addQuestion(AddQuestionDto addQuestionDto, Long userId);
    List<QuestionResponseDto> getQuestionBySessionId(Long sessionId);
}
