package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance,Long > {
}
