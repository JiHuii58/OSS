package com.example.webapphkh.services;

import com.example.webapphkh.models.LessonQuestion;
import com.example.webapphkh.repositories.LessonQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonQuestionService {
    @Autowired
    private LessonQuestionRepository lessonQuestionRepository;

    public List<LessonQuestion> getQuestionsByLesson(Long lessonId) {
        return lessonQuestionRepository.findByLessonId(lessonId);
    }

    public LessonQuestion addLessonQuestion(LessonQuestion lessonQuestion) {
        return lessonQuestionRepository.save(lessonQuestion);
    }
}
