package com.security.Jwt_service.service;

import com.security.Jwt_service.dto.response.statistic.StatisticForManager;

public interface StatisticService {
    StatisticForManager statisticForManager(String timeDigit);
}
