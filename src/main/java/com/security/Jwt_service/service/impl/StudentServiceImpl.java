package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.student.StudentCreateDto;
import com.security.Jwt_service.dto.response.student.StudentResponseDto;
import com.security.Jwt_service.entity.user.Role;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.exception.ResourceDuplicateException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.student.StudentMapper;
import com.security.Jwt_service.repository.RoleRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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

@RequiredArgsConstructor
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public StudentResponseDto createStudent(StudentCreateDto createDto) {
        if(studentRepository.existsByEmailOrStudentCode(createDto.getEmail(), createDto.getStudentCode())){
            throw new ResourceDuplicateException("Student", "email or student code", createDto.getStudentCode()+" "+ createDto.getEmail());
        }
        Student student= studentMapper.requestToEntity(createDto);
        passwordEncoder= new BCryptPasswordEncoder();
        Random random= new Random();
        String resetCode = String. format("%04d", random.nextInt(10000));
        Role role= roleRepository.findByName("STUDENT").get();
        User user = User.builder()
                .username(createDto.getStudentCode())
                .password(passwordEncoder.encode("12345678"))
                .resetPasswordCode(resetCode)
                .role(role)
                .build();
        student.setUser(user);
        return studentMapper.entityToResponse(studentRepository.save(student));
    }

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
}
