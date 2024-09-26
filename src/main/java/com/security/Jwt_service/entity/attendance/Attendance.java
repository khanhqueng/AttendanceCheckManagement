package com.security.Jwt_service.entity.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.entity.Base;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "attendance")

public class Attendance extends Base {
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "onClass_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime onClassTime;

    @Column(name = "understanding_rate")
    private BigDecimal underStandingRate;

    @Column(name = "efficient_rate")
    private BigDecimal efficientRate;

    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "session_id",referencedColumnName = "id", nullable = false)
    private Session session;
}
