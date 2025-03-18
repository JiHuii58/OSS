package com.example.webapphkh.controllers;


import com.example.webapphkh.models.ListeningLesson;
import com.example.webapphkh.services.ListeningLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/listening_lessons")
@CrossOrigin(origins = "http://localhost:3000")
public class ListeningLessonController {
    @Autowired
    private ListeningLessonService lessonService;

    @GetMapping
    public List<ListeningLesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public Optional<ListeningLesson> getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @PostMapping
    public ListeningLesson createLesson(@RequestBody ListeningLesson lesson) {
        return lessonService.saveLesson(lesson);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }
}
