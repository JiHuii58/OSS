package com.example.webapphkh.repositories;

import com.example.webapphkh.models.LessonReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonReadingRepository extends JpaRepository<LessonReading, Long> {
    List<LessonReading> findByLessonId(Long lessonId); // ✅ Truy vấn dựa trên lessonId
}
