package com.security.Jwt_service.repository;

import com.security.Jwt_service.entity.session.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySessionIdAndParentQuestionIsNull(Long sessionId);
    List<Question> findByParentQuestionId(Long parentId);
}
