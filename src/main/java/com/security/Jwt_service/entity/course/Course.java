package com.security.Jwt_service.entity.course;

import com.security.Jwt_service.entity.Base;
import com.security.Jwt_service.entity.classroom.Classroom;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "course")
public class Course extends Base {
    @Column(name = "name")
    private String name;

    @Column(name = "course_code")
    private String courseCode;

    @OneToMany(mappedBy = "course")
    private Set<Classroom> classrooms;
}
