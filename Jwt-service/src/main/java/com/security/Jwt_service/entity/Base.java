package com.security.Jwt_service.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    @Column(name = "create_at", nullable = false,updatable = false)
    private Instant createdAt= Instant.now();
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt= Instant.now();

}
