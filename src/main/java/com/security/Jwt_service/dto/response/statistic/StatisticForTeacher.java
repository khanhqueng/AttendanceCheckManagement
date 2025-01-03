package com.security.Jwt_service.dto.response.statistic;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticForTeacher {
    private Integer respondReceived;
    private Double efficientOfLesson;
    private Integer late;
    private Integer absentWithPermission;
    private Integer absentWithoutPermission;
    private Integer onTime;
    private Integer wellUnderstand;
    private Integer normalUnderstand;
    private Integer notWellUnderStand;
    private Integer badUnderstand;
}
