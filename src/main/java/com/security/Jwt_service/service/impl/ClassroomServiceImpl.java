package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.classroom.ClassroomCreateDto;
import com.security.Jwt_service.dto.response.classroom.ClassroomResponseDto;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.entity.course.Course;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.Teacher;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.classroom.ClassroomMapper;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.repository.CourseRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.repository.TeacherRepository;
import com.security.Jwt_service.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
