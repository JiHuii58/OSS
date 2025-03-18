package com.example.webapphkh.services;

import com.example.webapphkh.models.History;
import com.example.webapphkh.models.User;
import com.example.webapphkh.repositories.HistoryRepository;
import com.example.webapphkh.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository; // Thêm UserRepository để lấy thông tin user

    // Lấy toàn bộ lịch sử học tập của user
    public List<History> getHistoryByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return historyRepository.findByUser(user.get());
    }

    // Lấy lịch sử học tập theo trạng thái
    public List<History> getHistoryByStatus(Long userId, String status) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return historyRepository.findByUserAndStatus(user.get(), status);
    }

    // Tính điểm trung bình của user
    public ResponseEntity<Double> getAverageScore(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(0.0);
        }

        List<History> historyList = historyRepository.findByUser(user.get());
        if (historyList.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }

        int totalScore = historyList.stream().mapToInt(History::getScore).sum();
        double averageScore = (double) totalScore / historyList.size();
        return ResponseEntity.ok(averageScore);
    }

    // Lưu lịch sử học tập
    public ResponseEntity<String> saveHistory(Long userId, Long lessonId, int score, String status) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        History history = new History(user.get(), null, new Date(), score, status);
        historyRepository.save(history);
        return ResponseEntity.ok("Lịch sử học tập đã được lưu!");
    }

    // Xóa lịch sử học tập
    public ResponseEntity<String> deleteHistory(Long id) {
        Optional<History> historyOpt = historyRepository.findById(id);
        if (historyOpt.isPresent()) {
            historyRepository.deleteById(id);
            return ResponseEntity.ok("Lịch sử học tập đã bị xóa!");
        }
        return ResponseEntity.badRequest().body("Không tìm thấy lịch sử học tập!");
    }
}
