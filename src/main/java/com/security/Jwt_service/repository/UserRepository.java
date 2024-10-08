package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.user.User;
import com.security.Jwt_service.projection.EmailOnly;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
