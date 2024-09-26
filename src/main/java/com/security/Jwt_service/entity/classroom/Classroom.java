package com.security.Jwt_service.entity.classroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.entity.Base;
import com.security.Jwt_service.entity.course.Course;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.Teacher;
import jakarta.persistence.*;
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
@Table(name = "classroom")
public class Classroom extends Base {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "begin_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "allowed_lateTime")
    private int allowedLateTime;

    @ManyToOne
    @JoinColumn(name = "course_id",referencedColumnName = "id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id",referencedColumnName = "id", nullable = false)
    private Teacher teacher;

    @ManyToMany
    @JoinTable(name = "classroom_student",
            joinColumns = @JoinColumn(name = "classroom_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;

    @Column(name = "classRep_id")
    private Long classRepId;

    @OneToMany(mappedBy = "classroom")
    private Set<Session> sessions;

}
