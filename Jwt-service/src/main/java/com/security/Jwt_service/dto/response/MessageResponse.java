package com.security.Jwt_service.dto.response;

import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String content;
    private UserResponse user;
    @Getter
    @Builder
    @Setter
    @AllArgsConstructor
    public static class UserResponse {
        private Long id;
        private String username;
        private String email;
    }
}
