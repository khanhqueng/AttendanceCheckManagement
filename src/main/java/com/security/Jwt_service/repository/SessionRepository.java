package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Boolean existsByClassroomId(Long id);
}
