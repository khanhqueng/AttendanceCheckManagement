package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Boolean existsByName(String name);
    @Query("SELECT c FROM Classroom c " +
            "JOIN c.sessions s " +
            "WHERE s.representative_id = :repId " +
            "AND DATE(s.startTime) = :currentDate")
    List<Classroom> findByClassRepId(@Param("repId")Long repId, @Param("currentDate") LocalDate currentDate);
}
