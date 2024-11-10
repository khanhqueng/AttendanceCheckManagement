package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Boolean existsByEmailOrStudentCode(String email, String studentCode);
    Set<Student> findAllByIdIn(List<Long> ids);
    Optional<Student> findByUserId(Long userId);
    @Query("SELECT s FROM Student s " +
            "JOIN FETCH s.attendances a " +
            "JOIN FETCH a.session ses " +
            "JOIN FETCH ses.classroom c " +
            "WHERE s.studentCode = :studentCode AND (DATE(a.onClassTime) BETWEEN :startDate AND :endDate OR DATE(a.onClassTime) IS NULL)")
    Optional<Student> findByCodeAndDate(@Param("studentCode") String studentCode,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);
}
