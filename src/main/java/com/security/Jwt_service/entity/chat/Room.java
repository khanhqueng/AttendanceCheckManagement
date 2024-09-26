package com.security.Jwt_service.entity.chat;

import com.security.Jwt_service.entity.Base;
import com.security.Jwt_service.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "room")
public class Room extends Base {
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "room")
    private List<Message> messages;
}
