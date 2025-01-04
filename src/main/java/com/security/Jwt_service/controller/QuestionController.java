package com.security.Jwt_service.controller;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.request.question.AddQuestionDto;
import com.security.Jwt_service.dto.request.session.SessionCreateDto;
import com.security.Jwt_service.dto.response.question.QuestionResponseDto;
import com.security.Jwt_service.dto.response.session.SessionResponseCreateDto;
import com.security.Jwt_service.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Tag(name = "Question Controller")
public class QuestionController {
    private final QuestionService questionService;
    @Operation(summary = "Add question for a session", description = "API for add question")
    @PostMapping("/add")
    public ResponseEntity<QuestionResponseDto> addQuestion(@RequestBody AddQuestionDto dto, Authentication authentication){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(questionService.addQuestion(dto,userId), HttpStatus.OK);
    }
    @Operation(summary = "Get all questions for a session", description = "API for get question")
    @GetMapping("/{sessionId}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestion(@PathVariable(name = "sessionId") Long sessionId){
        return new ResponseEntity<>(questionService.getQuestionBySessionId(sessionId), HttpStatus.OK);
    }
}
