package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.user.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
