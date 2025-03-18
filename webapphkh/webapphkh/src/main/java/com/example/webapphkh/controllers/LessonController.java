package com.example.webapphkh.controllers;

import com.example.webapphkh.models.Lesson;
import com.example.webapphkh.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@CrossOrigin(origins = "http://localhost:3000")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")  //  API lấy chi tiết bài học theo ID
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        if (lesson != null) {
            return ResponseEntity.ok(lesson);
        } else {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy bài học
        }
    }

    @GetMapping("/level/{level}")
    public List<Lesson> getLessonsByLevel(@PathVariable String level) {
        return lessonService.getLessonsByLevel(level);
    }

    @GetMapping("/topic/{topic}")
    public List<Lesson> getLessonsByTopic(@PathVariable String topic) {
        return lessonService.getLessonsByTopic(topic);
    }

    @PostMapping
    public ResponseEntity<String> addLesson(@RequestBody Lesson lesson) {
        return lessonService.addLesson(lesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLesson(@PathVariable Long id, @RequestBody Lesson updatedLesson) {
        return lessonService.updateLesson(id, updatedLesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        return lessonService.deleteLesson(id);
    }
}
