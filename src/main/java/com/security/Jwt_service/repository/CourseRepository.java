package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Boolean existsByNameOrCourseCode(String name, String courseCode);
}
