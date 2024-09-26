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
@Table(name = "role")
public class Role extends Base {
    @Column(name = "name", nullable = false,unique = true)
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns  = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private Set<Permission> permissions;
    @OneToOne(mappedBy = "role")
    private Team team;
}
