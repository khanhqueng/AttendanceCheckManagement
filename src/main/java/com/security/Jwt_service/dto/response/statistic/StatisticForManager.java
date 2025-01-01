package com.security.Jwt_service.dto.response.statistic;

import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticForManager {
    private Integer absentWithPermission;
    private Integer LateForClass;
    private Integer absentWithoutPermission;
    private Integer onTime;
    private List<ClassroomResponseDto> topClassWithMostAbsentStudent;
}
