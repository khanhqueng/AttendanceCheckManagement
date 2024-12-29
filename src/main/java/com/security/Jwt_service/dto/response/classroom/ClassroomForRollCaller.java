package com.security.Jwt_service.dto.response.classroom;

import com.security.Jwt_service.dto.response.session.SessionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomForRollCaller {
    private Long id;
    private String name;
    private SessionResponseDto session;
}
