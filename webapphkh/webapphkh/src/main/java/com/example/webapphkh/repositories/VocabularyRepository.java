package com.example.webapphkh.repositories;

import com.example.webapphkh.models.Lesson;
import com.example.webapphkh.models.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findByTopic(String topic); // ✅ Truy vấn theo topic
    List<Vocabulary> findByLesson(Lesson lesson); // ✅ Truy vấn theo Lesson
    List<Vocabulary> findByIsLearned(boolean isLearned);

}
