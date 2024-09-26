package com.security.Jwt_service.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.entity.Base;
import com.security.Jwt_service.entity.classroom.Classroom;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "teacher")
public class Teacher extends Base {
    @Column(name = "email", unique = true, nullable = false)
    @Email
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "teacher_code", unique = true,nullable = false)
    private String teacherCode;

    @Column(name = "dob")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy = "teacher")
    private Set<Classroom> classrooms;
}
