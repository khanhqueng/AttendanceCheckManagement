package com.security.Jwt_service.entity.user;

import com.security.Jwt_service.entity.Base;
import com.security.Jwt_service.entity.chat.Message;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user")
public class User extends Base {
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name= "email", unique= true,nullable = false)
    @Email
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_team",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id")
    )
    private Set<Team> teams;
    @OneToMany(mappedBy = "user")
    private List<Message> messages;


}
