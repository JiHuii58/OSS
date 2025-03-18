package com.example.webapphkh.repositories;

import com.example.webapphkh.models.ListeningQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListeningQuestionRepository extends JpaRepository<ListeningQuestion, Long> {
    List<ListeningQuestion> findByLessonId(Long lessonId);
}