package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {

}
