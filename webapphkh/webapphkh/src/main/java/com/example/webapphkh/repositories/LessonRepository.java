package com.example.webapphkh.repositories;

import com.example.webapphkh.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByLevel(String level);
    List<Lesson> findByTopic(String topic);
}
