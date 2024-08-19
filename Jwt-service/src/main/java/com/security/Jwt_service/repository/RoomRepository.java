package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.chat.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
