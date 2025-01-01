package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomForRollCaller;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.dto.response.course.CourseResponseDto;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.entity.course.Course;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.Teacher;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.classroom.ClassroomMapper;
import com.security.Jwt_service.mapper.session.SessionMapper;
import com.security.Jwt_service.repository.*;
import com.security.Jwt_service.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final ClassroomRepository classroomRepository;
    private final ClassroomMapper classroomMapper;
    private final UserRepository userRepository;
    private final SessionMapper sessionMapper;
    @Override
    public ClassroomResponseDto createClassroom(ClassroomCreateDto createDto, List<Long> studentIds, Long teacherId, Long courseId) {
        if(classroomRepository.existsByName(createDto.getName())) throw new ResourceDuplicateException("Classroom", "name", createDto.getName());
        Set<Student> students= studentRepository.findAllByIdIn(studentIds);
        Teacher teacher= teacherRepository.findById(teacherId).orElseThrow(
                ()-> new ResourceNotFoundException("Teacher", "id", teacherId)
        );
        Course course= courseRepository.findById(courseId).orElseThrow(
                ()-> new ResourceNotFoundException("Course", "id", courseId)
        );
        Classroom classroom = classroomMapper.requestToEntity(createDto);
        classroom.setStudents(students);
        classroom.setTeacher(teacher);
        classroom.setCourse(course);
        return classroomMapper.entityToResponse(classroomRepository.save(classroom));
    }

    @Override
    public List<ClassroomResponseDto> getAllClassrooms() {
        return classroomRepository.findAll().stream().map(classroomMapper::entityToResponse).toList();
    }

    @Override
    public ClassroomResponseDto getClassroomById(Long id) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Classroom", "id", id)
        );
        return classroomMapper.entityToResponse(classroom);
    }

    @Override
    public ClassroomResponseDto addClassRepStudent(Long classId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                ()-> new ResourceNotFoundException("Student", "id", studentId)
        );
        Classroom classroom= classroomRepository.findById(classId).orElseThrow(
                ()-> new ResourceNotFoundException("Classroom", "id", classId)
        );
        if(!classroom.getStudents().contains(student)) throw new AppApiException(HttpStatus.BAD_REQUEST,"Student is not in this class");
        classroom.setClassRepId(studentId);
        return classroomMapper.entityToResponse(classroomRepository.save(classroom));
    }

    @Override
    public List<ClassroomForRollCaller> getClassroomCanRollCall(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User", "id", userId)
        );
        if(user.getStudent()==null){
            throw new AppApiException(HttpStatus.BAD_REQUEST,"Only student can do this roll call");
        }
        List<ClassroomForRollCaller> response= new ArrayList<>();
        Student student= user.getStudent();
        List<Classroom> classrooms = classroomRepository.findByClassRepId(student.getId(), LocalDate.now());
        for(Classroom classroom: classrooms){
            Set<Session> sessions= classroom.getSessions();
            Session allowedSession= sessions.stream().filter(session -> session.getStartTime().toLocalDate().equals(LocalDate.now())).findFirst().get();
            ClassroomForRollCaller classroomForRollCaller= classroomMapper.fromEntityToClassForRollCall(classroom);
            classroomForRollCaller.setSession(sessionMapper.entityToResponse(allowedSession));
            response.add(classroomForRollCaller);
        }
        return response;
    }

    @Override
    public ClassroomResponseDto addStudentToClass(Long classId, Long studentId) {
        Classroom classroom = classroomRepository.findById(classId).orElseThrow(
                ()-> new ResourceNotFoundException("Classroom", "id", classId)
        );
        Student student = studentRepository.findById(studentId).orElseThrow(
                ()-> new ResourceNotFoundException("Student", "id", studentId)
        );
        if(classroom.getStudents().contains(student)) throw new AppApiException(HttpStatus.BAD_REQUEST,"This student is already in this class");
        classroom.getStudents().add(student);
        return classroomMapper.entityToResponse(classroomRepository.save(classroom));
    }
}
