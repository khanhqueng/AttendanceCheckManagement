package com.security.Jwt_service.dto.response.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {
    private Long id;
    private String content;
    private LocalDateTime askedTime;
    private String user;
    private List<QuestionResponseDto> replies;
}
