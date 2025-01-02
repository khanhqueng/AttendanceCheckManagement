package com.security.Jwt_service.controller;
import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.response.classroom.ClassroomStudentIn;
import com.security.Jwt_service.dto.response.statistic.StatisticForManager;
import com.security.Jwt_service.service.ClassroomService;
import com.security.Jwt_service.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
@Tag(name = "Statistic Controller")
public class StatisticController {
    private final StatisticService statisticService;
    private final ClassroomService classroomService;
    @Operation(summary = "Get statistic for manager", description = "API for get statistic for manager")
    @GetMapping("/{timeDigit}")
    public ResponseEntity<StatisticForManager> createSessions(@PathVariable(name = "timeDigit") String timeDigit){
        return new ResponseEntity<>(statisticService.statisticForManager(timeDigit), HttpStatus.OK);
    }
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Get statistic attendances of a student", description = "API for get statistic attendance of a student")
    @GetMapping("/student")
    public ResponseEntity<List<ClassroomStudentIn>> getClassOfAStudent(Authentication authentication){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(classroomService.getAllClassAndAttendancesForAStudent(userId), HttpStatus.OK);
    }
}
