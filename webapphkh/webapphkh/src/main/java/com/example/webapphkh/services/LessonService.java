package com.example.webapphkh.services;

import com.example.webapphkh.models.Lesson;
import com.example.webapphkh.repositories.LessonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    private static final Logger logger = LoggerFactory.getLogger(LessonService.class);

    public List<Lesson> getAllLessons() {
        try {
            logger.info("📥 API getAllLessons() được gọi!");
            List<Lesson> lessons = lessonRepository.findAll();
            logger.info("✅ Số lượng bài học lấy về: {}", lessons.size());
            return lessons;
        } catch (Exception e) {
            logger.error("❌ Lỗi khi lấy danh sách bài học: ", e);
            throw new RuntimeException("Lỗi server: " + e.getMessage());
        }
    }

    public Lesson getLessonById(Long id) {
        try {
            logger.info("📥 API getLessonById({}) được gọi!", id);
            Optional<Lesson> lesson = lessonRepository.findById(id);
            if (lesson.isPresent()) {
                logger.info("✅ Bài học tìm thấy: {}", lesson.get().getTitle());
                return lesson.get();
            } else {
                logger.warn("⚠ Không tìm thấy bài học với ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("❌ Lỗi khi lấy bài học theo ID: ", e);
            throw new RuntimeException("Lỗi server: " + e.getMessage());
        }
    }

    public List<Lesson> getLessonsByLevel(String level) {
        try {
            logger.info("📥 API getLessonsByLevel({}) được gọi!", level);
            List<Lesson> lessons = lessonRepository.findByLevel(level);
            logger.info("✅ Số bài học ở level {}: {}", level, lessons.size());
            return lessons;
        } catch (Exception e) {
            logger.error("❌ Lỗi khi lấy bài học theo level: ", e);
            throw new RuntimeException("Lỗi server: " + e.getMessage());
        }
    }

    public List<Lesson> getLessonsByTopic(String topic) {
        try {
            logger.info("📥 API getLessonsByTopic({}) được gọi!", topic);
            List<Lesson> lessons = lessonRepository.findByTopic(topic);
            logger.info("✅ Số bài học có topic {}: {}", topic, lessons.size());
            return lessons;
        } catch (Exception e) {
            logger.error("❌ Lỗi khi lấy bài học theo topic: ", e);
            throw new RuntimeException("Lỗi server: " + e.getMessage());
        }
    }

    public ResponseEntity<String> addLesson(Lesson lesson) {
        try {
            logger.info("📥 API addLesson() được gọi!");
            logger.info("📌 Thêm bài học: {}", lesson.getTitle());
            lessonRepository.save(lesson);
            logger.info("✅ Bài học đã được thêm thành công!");
            return ResponseEntity.ok("Bài học đã được thêm!");
        } catch (Exception e) {
            logger.error("❌ Lỗi khi thêm bài học: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + e.getMessage());
        }
    }

    public ResponseEntity<String> updateLesson(Long id, Lesson updatedLesson) {
        try {
            logger.info("📥 API updateLesson({}, {}) được gọi!", id, updatedLesson.getTitle());
            Optional<Lesson> lessonOpt = lessonRepository.findById(id);
            if (lessonOpt.isPresent()) {
                Lesson lesson = lessonOpt.get();
                lesson.setTitle(updatedLesson.getTitle());
                lesson.setTopic(updatedLesson.getTopic());
                lesson.setLevel(updatedLesson.getLevel());
                lesson.setContent(updatedLesson.getContent());
                lesson.setAudioUrl(updatedLesson.getAudioUrl());
                lessonRepository.save(lesson);
                logger.info("✅ Bài học đã được cập nhật thành công!");
                return ResponseEntity.ok("Bài học đã được cập nhật!");
            } else {
                logger.warn("⚠ Không tìm thấy bài học với ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài học!");
            }
        } catch (Exception e) {
            logger.error("❌ Lỗi khi cập nhật bài học: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteLesson(Long id) {
        try {
            logger.info("📥 API deleteLesson({}) được gọi!", id);
            if (lessonRepository.existsById(id)) {
                lessonRepository.deleteById(id);
                logger.info("✅ Bài học đã bị xóa thành công!");
                return ResponseEntity.ok("Xóa bài học thành công!");
            } else {
                logger.warn("⚠ Không tìm thấy bài học với ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài học!");
            }
        } catch (Exception e) {
            logger.error("❌ Lỗi khi xóa bài học: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + e.getMessage());
        }
    }
}
