package com.security.Jwt_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder

public class TokenResponseDto implements Serializable {
    private String accToken;
    private String refreshToken;
}
