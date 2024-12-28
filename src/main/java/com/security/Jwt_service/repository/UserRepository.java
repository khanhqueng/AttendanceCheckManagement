package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    @Query("SELECT u FROM User u " +
            "LEFT JOIN u.student s " +
            "LEFT JOIN u.teacher t " +
            "LEFT JOIN u.manager m " +
            "WHERE (s.email = :email OR t.email = :email OR m.email = :email)")
    Optional<User> findUserByEmail(@Param("email") String email);

}
