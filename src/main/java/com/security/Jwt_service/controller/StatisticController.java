package com.security.Jwt_service.controller;
import com.security.Jwt_service.dto.response.statistic.StatisticForManager;
import com.security.Jwt_service.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
@Tag(name = "Statistic Controller")
public class StatisticController {
    private final StatisticService statisticService;
    @Operation(summary = "Get statistic for manager", description = "API for get statistic for manager")
    @GetMapping("/{timeDigit}")
    public ResponseEntity<StatisticForManager> createSessions(@PathVariable(name = "timeDigit") String timeDigit){
        return new ResponseEntity<>(statisticService.statisticForManager(timeDigit), HttpStatus.OK);
    }
}
