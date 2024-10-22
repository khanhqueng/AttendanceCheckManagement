package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Boolean existsByClassroomId(Long id);
    Optional<List<Session>> findAllByIsOver(Integer status);
}
