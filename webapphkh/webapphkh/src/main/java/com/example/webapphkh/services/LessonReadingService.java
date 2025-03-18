package com.example.webapphkh.services;

import com.example.webapphkh.models.LessonReading;
import com.example.webapphkh.repositories.LessonReadingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonReadingService {

    private final LessonReadingRepository lessonReadingRepository;
    private static final Logger logger = LoggerFactory.getLogger(LessonReadingService.class);

    public LessonReadingService(LessonReadingRepository lessonReadingRepository) {
        this.lessonReadingRepository = lessonReadingRepository;
    }

    public List<LessonReading> getReadingsByLesson(Long lessonId) {
        logger.info("🔍 Đang tìm bài đọc cho lessonId: {}", lessonId);

        List<LessonReading> readings = lessonReadingRepository.findByLessonId(lessonId);

        if (readings.isEmpty()) {
            logger.warn("⚠ Không tìm thấy bài đọc nào trong database cho lessonId: {}", lessonId);
        } else {
            logger.info("✅ Tìm thấy {} bài đọc trong database cho lessonId: {}", readings.size(), lessonId);
        }

        return readings;
    }
}
