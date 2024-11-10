package com.security.Jwt_service.dto.response.student;

import com.security.Jwt_service.dto.response.attend.SearchHistoryResponseDto;
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
public class StudentAttendanceHistoryResponseDto {
    private String studentCode;
    private String studentName;
    private List<SearchHistoryResponseDto> history;

}
