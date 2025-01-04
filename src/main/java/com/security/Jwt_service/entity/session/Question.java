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
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Question parentQuestion;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id", nullable = false)
    private Session session;

    @OneToMany(mappedBy = "parentQuestion", cascade = CascadeType.ALL)
    private List<Question> replies = new ArrayList<>();
}
