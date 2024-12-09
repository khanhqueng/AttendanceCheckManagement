package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.student.SearchAttendanceHistoryDto;
import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.request.user.UserCreateDto;
import com.security.Jwt_service.dto.response.attend.SearchHistoryResponseDto;
import com.security.Jwt_service.dto.response.student.StudentAttendanceHistoryResponseDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.dto.response.user.UserResponseDto;
import com.security.Jwt_service.dto.response.user.UserResponseFactory;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.entity.user.Role;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.student.StudentMapper;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.StudentService;
import com.security.Jwt_service.service.factorymethod.UserCreateMethod;
import com.security.Jwt_service.service.redis.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
@Transactional
public class StudentServiceImpl implements StudentService, UserCreateMethod {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final RoleRepository roleRepository;
//    private final BaseRedisService<String,String,String> redisTemplate;
    private static int count=0;
    private static AtomicInteger current=new AtomicInteger(0);
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Override
//    public StudentResponseDto createStudent(StudentCreateDto createDto) {
//        if(studentRepository.existsByEmailOrStudentCode(createDto.getEmail(), createDto.getStudentCode())){
//            throw new ResourceDuplicateException("Student", "email or student code", createDto.getStudentCode()+" "+ createDto.getEmail());
//        }
//        Student student= studentMapper.requestToEntity(createDto);
//        passwordEncoder= new BCryptPasswordEncoder();
//        Random random= new Random();
//        String resetCode = String. format("%04d", random.nextInt(10000));
//        Role role= roleRepository.findByName("STUDENT").get();
//        User user = User.builder()
//                .username(createDto.getStudentCode())
//                .password(passwordEncoder.encode("12345678"))
//                .resetPasswordCode(resetCode)
//                .role(role)
//                .build();
//        student.setUser(user);
//        return studentMapper.entityToResponse(studentRepository.save(student));
//    }

    @Override
    public List<StudentResponseDto> addStudentThroughExcel(MultipartFile excelFile) throws IOException {
        List<Student> studentList= new ArrayList<>();
        XSSFWorkbook workbook= new XSSFWorkbook(excelFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            DataFormatter formatter = new DataFormatter();
            XSSFRow row = worksheet.getRow(i);
            String studentCode= formatter.formatCellValue(row.getCell(0));
            String name= row.getCell(1).getStringCellValue();
            String email= row.getCell(2).getStringCellValue();
            String phone= formatter.formatCellValue(row.getCell(3));
            LocalDate dob= row.getCell(4).getDateCellValue()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            passwordEncoder= new BCryptPasswordEncoder();
            Random random= new Random();
            String resetCode = String. format("%04d", random.nextInt(10000));
            Role role= roleRepository.findByName("STUDENT").get();
            User user = User.builder()
                    .username(studentCode)
                    .password(passwordEncoder.encode("12345678"))
                    .resetPasswordCode(resetCode)
                    .role(role)
                    .build();
            Student student=Student.builder()
                    .name(name)
                    .studentCode(studentCode)
                    .date(dob)
                    .email(email)
                    .phoneNumber(phone)
                    .user(user)
                    .build();
            studentList.add(student);
        }
        studentRepository.saveAll(studentList);
        return studentList.stream().map(studentMapper::entityToResponse).toList();
    }

    @Override
    public List<StudentResponseDto> getAllStudent() {
        return studentRepository.findAll().stream().map(student -> studentMapper.entityToResponse(student)).toList();
    }

    @Override
    public StudentAttendanceHistoryResponseDto searchStudent(SearchAttendanceHistoryDto searchDto) {
        Student student=  studentRepository.findByCodeAndDate(searchDto.getStudentCode(), searchDto.getStartDate(),searchDto.getEndDate()).orElseThrow(
                ()-> new AppApiException( HttpStatus.BAD_REQUEST,"Student History doesn't exists in this range time")
        );
        StudentAttendanceHistoryResponseDto responseDto= new StudentAttendanceHistoryResponseDto();
        responseDto.setStudentName(student.getName());
        responseDto.setStudentCode(student.getStudentCode());

        List<SearchHistoryResponseDto>  history= new ArrayList<>();
        for(Attendance attendance: student.getAttendances()){
            SearchHistoryResponseDto searchHistory= SearchHistoryResponseDto.builder()
                    .status(attendance.getStatus())
                    .onClassTime(attendance.getOnClassTime())
                    .startTime(attendance.getSession().getStartTime())
                    .className(attendance.getSession().getClassroom().getName())
                    .no(attendance.getSession().getNo())
                    .build();
            history.add(searchHistory);
        }
        responseDto.setHistory(history);
        return responseDto;
    }

//    @Override
//    public String getAllStudentAdvance() {
//        current.incrementAndGet();
//        try{
//            log.info("Required Lock with current "+ current);
//            boolean isLocked= redisTemplate.acquireLock("Lock", "locked", 1,TimeUnit.SECONDS);
//            if(!isLocked){
//                // retry if lock key is used by other threads.
//                log.info("WAIT STUDENT");
//                return "PLEASE WAIT";
//            }
//            log.info("current: "+ current);
//            String item= redisTemplate.get("Student-All");
//            if(item!=null){
//                log.info("FROM CACHE {}", item);
//                return item;
//            }
//            count++;
//            String saveCache= "Get all student in "+count+" times";
//            redisTemplate.set("Student-All", saveCache);
//            log.info(saveCache);
//        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        finally {
//            log.info("Release Lock with current : "+current);
//            redisTemplate.releaseLock("Lock");
//        }
//
//        return null;
//    }

    @Override
    public UserResponseFactory createUser(UserCreateDto userCreateDto) {
        if(studentRepository.existsByEmailOrStudentCode(userCreateDto.getEmail(), userCreateDto.getRoleCode())){
            throw new ResourceDuplicateException("Student", "email or student code", userCreateDto.getEmail()+" "+ userCreateDto.getRoleCode());
        }
        Student student= studentMapper.requestToEntity(userCreateDto);
        passwordEncoder= new BCryptPasswordEncoder();
        Random random= new Random();
        String resetCode = String. format("%04d", random.nextInt(10000));
        Role role= roleRepository.findByName("STUDENT").get();
        User user = User.builder()
                .username(userCreateDto.getRoleCode())
                .password(passwordEncoder.encode("12345678"))
                .resetPasswordCode(resetCode)
                .role(role)
                .build();
        student.setUser(user);
        return studentMapper.entityToResponseFactory(studentRepository.save(student));
    }
}
