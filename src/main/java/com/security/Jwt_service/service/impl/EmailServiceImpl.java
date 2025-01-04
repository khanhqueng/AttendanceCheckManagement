package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.request.email.SendBatchDto;
import com.security.Jwt_service.dto.request.email.StudentSenderDto;
import com.security.Jwt_service.dto.response.attend.SearchHistoryResponseDto;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.repository.StudentRepository;
import com.security.Jwt_service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;
    @Override
    @Async
    public void sendAlertToMultipleStudent(Long classroomId, SendBatchDto dto) {
        Classroom classroom= classroomRepository.findById(classroomId).orElseThrow(
                ()-> new ResourceNotFoundException("Classroom", "id", classroomId)
        );
        for(StudentSenderDto studentSenderDto: dto.getStudentList()){
            sendEmail(studentSenderDto, classroom, dto.getOpinion());
        }
    }
    private void sendEmail(StudentSenderDto user, Classroom classroom, String opinion) {
        // Prepare the email content
        Student student = studentRepository.findByStudentCode(user.getStudentCode()).orElseThrow(
                ()-> new ResourceNotFoundException("Student", "code", user.getStudentCode())
        );
        List<Attendance> attendances = student.getAttendances().stream().filter(
                attendance -> attendance.getSession().getClassroom().getId().equals(classroom.getId())
        ).filter(attendance -> attendance.getStatus().equals(user.getTypeViolation())).toList();

        List<SearchHistoryResponseDto>  history= new ArrayList<>();
        attendances.forEach(attendance -> {
            SearchHistoryResponseDto searchHistoryResponseDto= SearchHistoryResponseDto.builder()
                    .no(attendance.getSession().getNo())
                    .status(attendance.getStatus())
                    .onClassTime(attendance.getOnClassTime())
                    .className(attendance.getSession().getClassroom().getName())
                    .startTime(attendance.getSession().getStartTime())
                    .build();
            history.add(searchHistoryResponseDto);
        });

        Context context = new Context();
        context.setVariable("studentName", student.getName());
        context.setVariable("type",user.getTypeViolation());
        context.setVariable("className",classroom.getName());
        context.setVariable("numViolation", user.getNumberViolations());
        context.setVariable("allowedViolation", user.getMaximumViolationAllowed());
        context.setVariable("details", history);
        context.setVariable("opinion", opinion);

        String htmlContent = templateEngine.process("email-template", context);

        // Create the email
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("khanhyop@yopmail.com");
            helper.setSubject("Attendance Alert");
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email to khanhyop", e);
        }
    }
}
