package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.question.AddQuestionDto;
import com.security.Jwt_service.dto.response.question.QuestionResponseDto;
import com.security.Jwt_service.entity.session.Question;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.question.QuestionMapper;
import com.security.Jwt_service.repository.QuestionRepository;
import com.security.Jwt_service.repository.SessionRepository;
import com.security.Jwt_service.repository.UserRepository;
import com.security.Jwt_service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionResponseDto addQuestion(AddQuestionDto addQuestionDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Session session = sessionRepository.findById(addQuestionDto.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session", "id", addQuestionDto.getSessionId()));
        Question question = new Question();
        question.setContent(addQuestionDto.getContent());
        question.setAskedTime(LocalDateTime.now());
        question.setUser(user);
        question.setSession(session);
        if (addQuestionDto.getParentId() != null) {
            Question parentQuestion = questionRepository.findById(addQuestionDto.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent question", "id", addQuestionDto.getParentId()));
            question.setParentQuestion(parentQuestion);
        }
        return questionMapper.entityToResponse(questionRepository.save(question));
    }

    @Override
    public List<QuestionResponseDto> getQuestionBySessionId(Long sessionId) {
        List<Question> question = questionRepository.findBySessionIdAndParentQuestionIsNull(sessionId);
        return question.stream().map(questionMapper::entityToResponse).toList();
    }
}
