package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Boolean existsByEmailOrStudentCode(String email, String studentCode);
    Set<Student> findAllByIdIn(List<Long> ids);
    Optional<Student> findByUserId(Long userId);

}
