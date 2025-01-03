package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.user.Student;
import com.security.Jwt_service.entity.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Boolean existsByEmailOrTeacherCode(String email, String teacherCode);
    Optional<Teacher> findByUserId(Long userId);
}
