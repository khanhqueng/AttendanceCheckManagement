package com.security.Jwt_service.entity.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.security.Jwt_service.entity.Base;
import com.security.Jwt_service.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "question")

public class Question extends Base {
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "asked_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime askedTime;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id", nullable = false)
    private Session session;
}
