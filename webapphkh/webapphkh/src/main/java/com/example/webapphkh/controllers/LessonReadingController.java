package com.example.webapphkh.controllers;

import com.example.webapphkh.models.LessonReading;
import com.example.webapphkh.services.LessonReadingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson_readings")
@CrossOrigin(origins = "http://localhost:3000")
public class LessonReadingController {

    private final LessonReadingService lessonReadingService;
    private static final Logger logger = LoggerFactory.getLogger(LessonReadingController.class);

    public LessonReadingController(LessonReadingService lessonReadingService) {
        this.lessonReadingService = lessonReadingService;
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<?> getLessonReadings(
            @PathVariable Long lessonId,
            @RequestHeader(name = "Authorization", required = false) String authHeader) {

        logger.info("📥 Nhận yêu cầu lấy bài đọc cho lessonId: {}", lessonId);

        // Log xem token có được gửi từ React không
        if (authHeader == null || authHeader.isEmpty()) {
            logger.warn("🚨 Không có Authorization token trong request!");
        } else {
            logger.info("🔑 Nhận được Authorization token: {}", authHeader);
        }

        List<LessonReading> readings = lessonReadingService.getReadingsByLesson(lessonId);

        if (readings.isEmpty()) {
            logger.warn("⚠ Không tìm thấy bài đọc nào cho lessonId: {}", lessonId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài đọc nào!");
        }

        logger.info("✅ Trả về {} bài đọc cho lessonId: {}", readings.size(), lessonId);
        return ResponseEntity.ok(readings);
    }
}
