package com.example.webapphkh.services;

import com.example.webapphkh.models.Progress;
import com.example.webapphkh.models.User;
import com.example.webapphkh.models.Lesson;
import com.example.webapphkh.repositories.ProgressRepository;
import com.example.webapphkh.repositories.UserRepository;
import com.example.webapphkh.repositories.LessonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class ProgressService {
    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    // Lấy tiến độ học tập của user
    public List<Progress> getProgressByUserId(Long userId) {
        return progressRepository.findByUser_Id(userId);
    }

    // Tính phần trăm bài học đã hoàn thành
    public ResponseEntity<Double> getCompletionRate(Long userId) {
        List<Progress> completed = progressRepository.findByUser_IdAndStatus(userId, "completed");
        List<Progress> allProgress = progressRepository.findByUser_Id(userId);

        if (allProgress.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }
        double completionRate = ((double) completed.size() / allProgress.size()) * 100;
        return ResponseEntity.ok(completionRate);
    }

    // Tính điểm trung bình của bài kiểm tra
    public ResponseEntity<Double> getAverageScore(Long userId) {
        List<Progress> progressList = progressRepository.findByUser_Id(userId);
        if (progressList.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }

        int totalScore = 0;
        int count = 0;

        for (Progress progress : progressList) {
            if (progress.getScore() > 0) {
                totalScore += progress.getScore();
                count++;
            }
        }

        double averageScore = count == 0 ? 0 : (double) totalScore / count;
        return ResponseEntity.ok(averageScore);
    }

    // Cập nhật tiến độ học tập
    public ResponseEntity<String> updateProgress(Long userId, Long lessonId, int score) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Lesson> lessonOpt = lessonRepository.findById(lessonId);

        if (userOpt.isEmpty() || lessonOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User hoặc Lesson không tồn tại!");
        }

        List<Progress> progressList = progressRepository.findByUser_IdAndStatus(userId, "in-progress");

        for (Progress progress : progressList) {
            if (progress.getLesson().getId().equals(lessonId)) {
                progress.setStatus("completed");
                progress.setScore(score);
                progress.setLastUpdated(new Date());
                progressRepository.save(progress);
                return ResponseEntity.ok("Tiến độ học đã cập nhật!");
            }
        }

        // Nếu chưa có tiến độ nào, tạo mới
        Progress newProgress = new Progress(userOpt.get(), lessonOpt.get(), "completed", score, new Date());
        progressRepository.save(newProgress);
        return ResponseEntity.ok("Tiến độ học đã được tạo mới!");
    }
}
