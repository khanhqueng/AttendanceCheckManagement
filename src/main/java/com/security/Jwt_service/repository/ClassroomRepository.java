package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Boolean existsByName(String name);
}
