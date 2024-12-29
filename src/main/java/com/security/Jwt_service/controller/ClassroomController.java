package com.security.Jwt_service.controller;

import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomForRollCaller;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classroom")
@RequiredArgsConstructor
@Tag(name = "Classroom Controller")
public class ClassroomController {
    private final ClassroomService classroomService;
    @Operation(summary = "Create classroom", description = "API for create new classroom")
    @PostMapping
    public ResponseEntity<ClassroomResponseDto> createClassroom(@RequestBody @Valid ClassroomCreateDto dto,
                                                                @RequestParam(name = "student") List<Long> studentId,
                                                                @RequestParam(name = "teacher") Long teacherId,
                                                                @RequestParam(name = "course") Long courseId){
        return new ResponseEntity<>(classroomService.createClassroom(dto, studentId, teacherId, courseId), HttpStatus.CREATED);
    }
    @Operation(summary = "Get all classrooms", description = "API for get all classrooms")
    @GetMapping
    public ResponseEntity<List<ClassroomResponseDto>> getAllClassrooms(){
        return new ResponseEntity<>(classroomService.getAllClassrooms(), HttpStatus.OK);
    }
    @Operation(summary = "Get classroom by id", description = "API for get classroom by id")
    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponseDto> getClassroomById(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(classroomService.getClassroomById(id), HttpStatus.OK);
    }
    @Operation(summary = "Update classroom representative", description = "API for update rep classroom")
    @PostMapping("/{classId}/{studentId}")
    public ResponseEntity<ClassroomResponseDto> addRepId(@PathVariable(name = "classId") Long classId,
                                                         @PathVariable(name = "studentId") Long studentId){
        return new ResponseEntity<>(classroomService.addClassRepStudent(classId, studentId), HttpStatus.OK);
    }
    @Operation(summary = "Get classrooms can roll call", description = "API for get classroom can roll call")
    @GetMapping("/roll-call")
    public ResponseEntity<List<ClassroomForRollCaller>> getClassCanRollCall(Authentication authentication){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(classroomService.getClassroomCanRollCall(userId), HttpStatus.OK);
    }



}
