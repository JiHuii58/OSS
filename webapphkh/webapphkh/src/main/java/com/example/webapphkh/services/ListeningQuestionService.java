package com.example.webapphkh.services;


import com.example.webapphkh.models.ListeningQuestion;
import com.example.webapphkh.repositories.ListeningQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListeningQuestionService {
    @Autowired
    private ListeningQuestionRepository questionRepository;

    public List<ListeningQuestion> getQuestionsByLessonId(Long lessonId) {
        return questionRepository.findByLessonId(lessonId);
    }

    public ListeningQuestion saveQuestion(ListeningQuestion question) {
        return questionRepository.save(question);
    }
}

