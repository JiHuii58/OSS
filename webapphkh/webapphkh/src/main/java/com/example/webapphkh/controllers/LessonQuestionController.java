package com.example.webapphkh.controllers;

import com.example.webapphkh.models.LessonQuestion;
import com.example.webapphkh.services.LessonQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lesson_questions")
@CrossOrigin(origins = "http://localhost:3000")
public class LessonQuestionController {
    @Autowired
    private LessonQuestionService lessonQuestionService;

    @GetMapping(value = "/{lessonId}", produces = "application/json")
    public List<LessonQuestion> getLessonQuestions(@PathVariable Long lessonId) {
        log.info("Nhận yêu cầu lấy câu hỏi cho bài học: " + lessonId);
        return lessonQuestionService.getQuestionsByLesson(lessonId);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public LessonQuestion addLessonQuestion(@RequestBody LessonQuestion lessonQuestion) {
        log.info("Thêm câu hỏi mới: " + lessonQuestion.getQuestion());
        return lessonQuestionService.addLessonQuestion(lessonQuestion);
    }
}
