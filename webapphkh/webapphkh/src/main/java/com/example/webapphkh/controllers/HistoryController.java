package com.example.webapphkh.controllers;

import com.example.webapphkh.models.History;
import com.example.webapphkh.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping("/{userId}")
    public List<History> getHistoryByUserId(@PathVariable Long userId) {
        return historyService.getHistoryByUserId(userId);
    }

    @GetMapping("/{userId}/status/{status}")
    public List<History> getHistoryByStatus(@PathVariable Long userId, @PathVariable String status) {
        return historyService.getHistoryByStatus(userId, status);
    }

    @GetMapping("/{userId}/average-score")
    public ResponseEntity<Double> getAverageScore(@PathVariable Long userId) {
        return historyService.getAverageScore(userId);
    }

    @PostMapping("/{userId}/{lessonId}")
    public ResponseEntity<String> saveHistory(@PathVariable Long userId, @PathVariable Long lessonId,
                                              @RequestParam int score, @RequestParam String status) {
        return historyService.saveHistory(userId, lessonId, score, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistory(@PathVariable Long id) {
        return historyService.deleteHistory(id);
    }
}
