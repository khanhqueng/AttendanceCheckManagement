package com.security.Jwt_service.dto.response.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticForTeacher {
    private Integer respondReceived;
    private Integer efficientOfLesson;

}
