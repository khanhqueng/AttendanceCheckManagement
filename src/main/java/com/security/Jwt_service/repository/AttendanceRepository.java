package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance,Long > {
    Boolean existsByStudentIdAndSessionId(Long studentId, Long sessionId);
    @Query("SELECT a FROM Attendance a JOIN FETCH a.session s WHERE s.startTime BETWEEN :startTime AND :endTime")
    List<Attendance> findAllAttendancesByTimeRange(@Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);
    @Query("SELECT a FROM Attendance a "+
            "JOIN FETCH a.session s " +
            "JOIN FETCH s.classroom c "+
            "WHERE a.student.id = :studentId AND c.id = :classroomId")
    List<Attendance> findByStudentIdAndClassroomId(@Param("studentId") Long studentId,
                                                   @Param("classroomId") Long classroomId);
    Optional<Attendance> findByStudentIdAndSessionId(Long studentId, Long sessionId);
}