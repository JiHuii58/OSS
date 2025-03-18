package com.example.webapphkh.controllers;


import com.example.webapphkh.models.ListeningQuestion;
import com.example.webapphkh.services.ListeningQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listening_questions")
@CrossOrigin(origins = "http://localhost:3000")
public class ListeningQuestionController {
    @Autowired
    private ListeningQuestionService questionService;

    @GetMapping("/lesson/{lessonId}")
    public List<ListeningQuestion> getQuestionsByLessonId(@PathVariable Long lessonId) {
        return questionService.getQuestionsByLessonId(lessonId);
    }

    @PostMapping
    public ListeningQuestion createQuestion(@RequestBody ListeningQuestion question) {
        return questionService.saveQuestion(question);
    }
}

