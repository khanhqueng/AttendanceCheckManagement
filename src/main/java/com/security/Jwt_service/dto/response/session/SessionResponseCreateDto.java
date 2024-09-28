package com.security.Jwt_service.dto.response.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SessionResponseCreateDto {
    Set<SessionResponseDto> sessions;

}
