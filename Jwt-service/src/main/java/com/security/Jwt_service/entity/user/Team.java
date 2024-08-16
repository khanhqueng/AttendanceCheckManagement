package com.security.Jwt_service.entity.user;

import com.security.Jwt_service.entity.Base;
import jakarta.persistence.*;
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
@Table(name = "team")
public class Team extends Base {
    @Column(name = "name", nullable = false,unique = true)
    private String name;
    @ManyToMany(mappedBy = "teams")
    private Set<User> users;
    @OneToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;
}
