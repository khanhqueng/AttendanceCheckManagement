package com.security.Jwt_service.entity.chat;

import com.security.Jwt_service.entity.Base;
import com.security.Jwt_service.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "message")
public class Message extends Base {
    @Column(name = "content", nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false, referencedColumnName = "id")
    private Room room;
}
