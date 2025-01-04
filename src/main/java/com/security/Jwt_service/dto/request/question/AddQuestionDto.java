package com.security.Jwt_service.dto.request.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddQuestionDto {
    private Long sessionId;
    private String content;
    private Long parentId;
}
