package com.security.Jwt_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.Jwt_service.config.security.CustomUserDetails;
import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomForRollCaller;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/classroom")
@RequiredArgsConstructor
@Tag(name = "Classroom Controller")
public class ClassroomController {
    private final ClassroomService classroomService;
    @Autowired
    private ObjectMapper objectMapper;
//    @Operation(summary = "Create classroom", description = "API for create new classroom")
//    @PostMapping
//    public ResponseEntity<ClassroomResponseDto> createClassroom(@RequestBody @Valid ClassroomCreateDto dto,
//                                                                @RequestParam(name = "student") List<Long> studentId,
//                                                                @RequestParam(name = "teacher") Long teacherId,
//                                                                @RequestParam(name = "course") Long courseId){
//        return new ResponseEntity<>(classroomService.createClassroom(dto, studentId, teacherId, courseId), HttpStatus.CREATED);
//    }
    @Operation(summary = "Create classroom", description = "API for create new classroom")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ClassroomResponseDto> createClassroom(@RequestParam("classroom-info") String classInfo,
                                                                @RequestParam(name = "student-file") MultipartFile excelFile) throws JsonProcessingException {
        ClassroomCreateDto dto = objectMapper.readValue(classInfo, ClassroomCreateDto.class);
        return new ResponseEntity<>(classroomService.createClassroomWithStudentThroughExcel(dto, excelFile), HttpStatus.CREATED);
    }
    @Operation(summary = "Update classroom", description = "API for update classroom")
    @PutMapping("/update/{classroomId}")
    public ResponseEntity<ClassroomResponseDto> updateClassroom(@RequestBody ClassroomCreateDto updateDto,
                                                                @PathVariable(name = "classroomId") Long classroomId)  {
        return new ResponseEntity<>(classroomService.updateClassroom(updateDto,classroomId), HttpStatus.OK);
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

    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Get classrooms can roll call", description = "API for get classroom can roll call")
    @GetMapping("/roll-call")
    public ResponseEntity<List<ClassroomForRollCaller>> getClassCanRollCall(Authentication authentication){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(classroomService.getClassroomCanRollCall(userId), HttpStatus.OK);
    }

    @Operation(summary = "Add a student to a classroom", description = "API for add a student to a classroom")
    @PutMapping("/{classId}/{studentId}")
    public ResponseEntity<ClassroomResponseDto> addStudentToClass(@PathVariable(name = "classId") Long classId,
                                                                  @PathVariable(name = "studentId") Long studentId){
        return new ResponseEntity<>(classroomService.addStudentToClass(classId, studentId), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Get classrooms by user (student) id", description = "API for get classroom by user (student) id")
    @GetMapping("/student")
    public ResponseEntity<List<ClassroomResponseDto>> getAllClassroomByStudentId(Authentication authentication){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(classroomService.getAllClassByStudentId(userId), HttpStatus.OK);
    }
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "Get classrooms by user (teacher) id", description = "API for get classroom by user (teacher) id")
    @GetMapping("/teacher")
    public ResponseEntity<List<ClassroomResponseDto>> getAllClassroomByTeacherId(Authentication authentication){
        Long userId= ((CustomUserDetails) authentication.getPrincipal()).getId();
        return new ResponseEntity<>(classroomService.getAllClassByTeacherId(userId), HttpStatus.OK);
    }

}
