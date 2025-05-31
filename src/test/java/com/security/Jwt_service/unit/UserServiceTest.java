package com.security.Jwt_service.unit;

import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.mapper.student.StudentMapper;
import com.security.Jwt_service.mapper.teacher.TeacherMapper;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.repository.TeacherRepository;
import com.security.Jwt_service.repository.UserRepository;
import com.security.Jwt_service.service.factorymethod.UserCreateMethod;
import com.security.Jwt_service.service.impl.StudentServiceImpl;
import com.security.Jwt_service.service.impl.TeacherServiceImpl;
import com.security.Jwt_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private UserServiceImpl userService;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateUserMethod_Student() {
        // create mock dto
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setRoleName("Student");

        UserCreateMethod result = userService.createUserMethod(userCreateDto);

        assertNotNull(result);
        assertTrue(result instanceof StudentServiceImpl);

    }

    @Test
    void testCreateUserMethod_Teacher() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setRoleName("Teacher");

        // Act
        UserCreateMethod result = userService.createUserMethod(userCreateDto);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof TeacherServiceImpl);
    }

    @Test
    void testCreateUserMethod_InvalidRole() {

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setRoleName("InvalidRole");

        AppApiException exception = assertThrows(
                AppApiException.class,
                () -> userService.createUserMethod(userCreateDto)
        );

        assertEquals("Invalid role", exception.getMessage());
    }
}
