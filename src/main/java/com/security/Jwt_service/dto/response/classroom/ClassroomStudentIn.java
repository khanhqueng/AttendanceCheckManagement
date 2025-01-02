package com.security.Jwt_service.dto.response.classroom;

import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomStudentIn {
    private Long id;
    private String name;
    private Integer allowedLateTime;
    private Integer allowedAbsent;
    private List<AttendanceResponseDto> attendances;
}
