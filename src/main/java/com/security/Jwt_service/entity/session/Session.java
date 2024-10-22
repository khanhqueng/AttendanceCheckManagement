package com.security.Jwt_service.entity.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.entity.Base;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.entity.classroom.Classroom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "session")
public class Session extends Base {
    @Column(name = "no", nullable = false)
    private int no;

    @Column(name = "start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Column(name = "is_over")
    private Integer isOver;

    @ManyToOne
    @JoinColumn(name = "classroom_id",referencedColumnName = "id", nullable = false)
    private Classroom classroom;

    @Column(name = "representative_id")
    private Long representative_id;

    @OneToMany(mappedBy = "session")
    private Set<Attendance> attendances;

    @OneToMany(mappedBy = "session")
    private Set<Question> questions;
}
