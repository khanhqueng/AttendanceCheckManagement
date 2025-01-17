package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.request.user.UserUpdatePasswordDto;
import com.security.Jwt_service.dto.request.user.UserUpdateVerify;
import com.security.Jwt_service.dto.response.user.UserResponseDto;
import com.security.Jwt_service.entity.user.*;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.student.StudentMapper;
import com.security.Jwt_service.mapper.teacher.TeacherMapper;
import com.security.Jwt_service.mapper.user.UserMapper;
import com.security.Jwt_service.repository.*;
import com.security.Jwt_service.service.UserService;
import com.security.Jwt_service.service.factorymethod.UserCreateMethod;
import com.security.Jwt_service.service.redis.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;
    private final UserMapper userMapper;
    private final ClassroomRepository classroomRepository;
    private final BaseRedisService<String, String, String> redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender emailSender;

//    @Override
//    public UserResponseDto createUser(UserCreateDto createDto) {
//        if(userRepository.existsByUsername(createDto.getUsername())){
//            throw new ResourceDuplicateException("User", "username", createDto.getUsername());
//        }
//        User user= userMapper.requestToEntity(createDto);
//        passwordEncoder= new BCryptPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(createDto.getPassword()));
//        Role role= Role.builder().name(createDto.getRolName()).build();
//        user.setRole(role);
//        return  userMapper.entityToResponse(userRepository.save(user));
//    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        UserResponseDto response= userMapper.entityToResponse(user);
        if(user.getStudent()!=null){
            Student student = user.getStudent();
            response.setName(student.getName());
            response.setDob(student.getDate());
            response.setPhoneNumber(student.getPhoneNumber());
            response.setRoleCode(student.getStudentCode());
        }
        else if(user.getTeacher()!=null){
            Teacher teacher = user.getTeacher();
            response.setName(teacher.getName());
            response.setDob(teacher.getDate());
            response.setPhoneNumber(teacher.getPhoneNumber());
            response.setRoleCode(teacher.getTeacherCode());
        } else if (user.getManager()!=null) {
            Manager manager= user.getManager();
            response.setName(manager.getName());
            response.setDob(manager.getDate());
            response.setPhoneNumber(manager.getPhoneNumber());
        }
        return response;
    }

    @Override
    public UserResponseDto updateUser(Long userId, String email) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDto changeRole(Long userId, String nameRole) {
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
        Role newRole= roleRepository.findByName(nameRole).orElseThrow(
                ()-> new ResourceNotFoundException("Role", "name", nameRole)
        );
        user.setRole(newRole);
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    public UserCreateMethod createUserMethod(UserCreateDto userCreateDto) {
        if(userCreateDto.getRoleName().equals("Student")) return new StudentServiceImpl(studentRepository,studentMapper,roleRepository,classroomRepository);
        if(userCreateDto.getRoleName().equals("Teacher")) return new TeacherServiceImpl(teacherRepository,roleRepository,teacherMapper);
        throw new AppApiException(HttpStatus.BAD_REQUEST,"Invalid role");
    }

    @Override
    @Async
    public CompletableFuture<String> genCodeForChangePassword(UserUpdatePasswordDto updatePasswordDto) {
        User user = userRepository.findUserByEmail(updatePasswordDto.getEmail()).orElseThrow(
                ()-> new ResourceNotFoundException("User", "email", updatePasswordDto.getEmail())
        );
        Random random = new Random();
        String randomString = random.ints(4, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        CompletableFuture<Void> redisTask = CompletableFuture.runAsync(
                ()->{
                    redisTemplate.set(updatePasswordDto.getEmail(), randomString);
                    redisTemplate.setTimeToLive(updatePasswordDto.getEmail(),5);
                }
        );
        CompletableFuture<Void> sendMailTask= CompletableFuture.runAsync(
                ()->{
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo("khanhyop@yopmail.com");
                    message.setSubject("Change Password");
                    message.setText(String.format("Your recovery code is %s", randomString));
                    emailSender.send(message);
                }
        );
        return CompletableFuture.completedFuture("Sent success");
    }

    @Override
    public UserResponseDto changePassword(UserUpdateVerify updateVerify) {

        User user = userRepository.findUserByEmail(updateVerify.getEmail()).orElseThrow(
                ()-> new ResourceNotFoundException("user", "email", updateVerify.getEmail())
        );
        passwordEncoder= new BCryptPasswordEncoder();
        if(passwordEncoder.matches(updateVerify.getNewPassword(), user.getPassword()))
            throw new AppApiException(HttpStatus.BAD_REQUEST,"New password is same as old password");
        user.setPassword(passwordEncoder.encode(updateVerify.getNewPassword()));
        return userMapper.entityToResponse(userRepository.save(user));
    }

    @Override
    public Boolean verifyCode(UserUpdatePasswordDto updatePasswordDto, String code) {
        String verifyCode= redisTemplate.get(updatePasswordDto.getEmail());
        if(verifyCode==null) throw new AppApiException(HttpStatus.BAD_REQUEST,"Email not correct or code is expired");
        if(!verifyCode.equals(code))
            return false;
        return true;
    }
}
