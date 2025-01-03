package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.response.statistic.StatisticForManager;
import com.security.Jwt_service.dto.response.statistic.StatisticForTeacher;

public interface StatisticService {
    StatisticForManager statisticForManager(String timeDigit);
    StatisticForTeacher statisticForTeacher(Long classroomId);
}
