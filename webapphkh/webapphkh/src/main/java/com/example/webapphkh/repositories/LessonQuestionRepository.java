package com.example.webapphkh.repositories;

import com.example.webapphkh.models.LessonQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonQuestionRepository extends JpaRepository<LessonQuestion, Long> {
    List<LessonQuestion> findByLessonId(Long lessonId);
}
