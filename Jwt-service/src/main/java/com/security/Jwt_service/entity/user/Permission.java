package com.security.Jwt_service.entity.user;

import com.security.Jwt_service.entity.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
@Table(name = "permission")
public class Permission extends Base {
    @Column(name = "name", nullable = false,unique = true)
    private String name;
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}
