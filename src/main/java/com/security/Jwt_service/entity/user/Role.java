package com.security.Jwt_service.entity.user;

import com.security.Jwt_service.entity.Base;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "role")
public class Role extends Base {
    @Column(name = "name", nullable = false,unique = true)
    private String name;
    @OneToMany(mappedBy = "role")
    private Set<User> users;
}
