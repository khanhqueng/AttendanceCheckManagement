package com.security.Jwt_service.dto.request.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SessionCreateDto {
    @NotNull(message = "number of sessions must not be null")
    private int numberSessions;
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "on-class time must not be null")
    private LocalTime onClassTime;
    @NotNull(message = "end time must not be null")
    private LocalTime endClassTime;
    @NotNull(message = "classroom id must not be null")
    private Long classRoomId;
}
