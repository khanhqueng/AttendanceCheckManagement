package com.security.Jwt_service.controller;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.request.attendance.TimeRangeDto;
import com.security.Jwt_service.dto.request.course.CourseCreateDto;
import com.security.Jwt_service.dto.request.survey.SurveyRequestDto;
import com.security.Jwt_service.dto.response.attend.AttendanceResponseDto;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.service.AttendanceService;
import com.security.Jwt_service.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance Controller")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final SurveyService surveyService;
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Add attendance ( Only authenticated student) ", description = "API for add new attendance")
    @PostMapping("/{session_id}")
    public ResponseEntity<AttendanceResponseDto> createAttendance(Authentication authentication, @PathVariable(name = "session_id") Long id,
                                                                  @RequestBody TimeRangeDto dto){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(attendanceService.attendStudent(id,userId, dto.getStartTime(), dto.getEndTime()), HttpStatus.CREATED);
    }

    @Operation(summary = "Update absent student", description = "API for update absent student")
    @PostMapping("/{session_id}/{student_id}")
    public ResponseEntity<AttendanceResponseDto> updateAbsentStudent(@PathVariable(name = "session_id") Long sessionId,
                                                              @PathVariable(name = "student_id") Long studentId){
        return new ResponseEntity<>(attendanceService.updateAbsentStudent(sessionId,studentId), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Get All Attendance of a student", description = "API for Get All Attendance of a student")
    @GetMapping
    public ResponseEntity<List<AttendanceResponseDto>> getAllAttendance(Authentication authentication){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(attendanceService.getAllAttendanceByStudentId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Get all attendances in a session", description = "API for get attendances in a session")
    @GetMapping("/{session_id}")
    public ResponseEntity<List<AttendanceResponseDto>> getAllAttendanceBySessionId(@PathVariable(name = "session_id") Long id){
        return new ResponseEntity<>(attendanceService.getAttendanceBySessionId(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete all attendances", description = "API for delete all attendances")
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllAttendances(){
        attendanceService.deleteALlAttendance();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Add survey for session ", description = "API for add survey info for session")
    @PutMapping("/survey")
    public ResponseEntity<AttendanceResponseDto> addSurvey(Authentication authentication, @RequestBody @Valid SurveyRequestDto requestDto){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(surveyService.addSurveyInfo(requestDto,userId), HttpStatus.OK);
    }

}
