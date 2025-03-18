package com.example.webapphkh.controllers;

import com.example.webapphkh.models.Progress;
import com.example.webapphkh.services.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {
    @Autowired
    private ProgressService progressService;

    @GetMapping("/{userId}")
    public List<Progress> getProgressByUserId(@PathVariable Long userId) {
        return progressService.getProgressByUserId(userId);
    }

    @GetMapping("/{userId}/completion-rate")
    public ResponseEntity<Double> getCompletionRate(@PathVariable Long userId) {
        return progressService.getCompletionRate(userId);
    }

    @GetMapping("/{userId}/average-score")
    public ResponseEntity<Double> getAverageScore(@PathVariable Long userId) {
        return progressService.getAverageScore(userId);
    }

    @PutMapping("/{userId}/{lessonId}")
    public ResponseEntity<String> updateProgress(@PathVariable Long userId, @PathVariable Long lessonId, @RequestParam int score) {
        return progressService.updateProgress(userId, lessonId, score);
    }
}
