package com.example.webapphkh.services;

import com.example.webapphkh.models.ListeningLesson;
import com.example.webapphkh.repositories.ListeningLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ListeningLessonService {
    @Autowired
    private ListeningLessonRepository lessonRepository;

    public List<ListeningLesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Optional<ListeningLesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    public ListeningLesson saveLesson(ListeningLesson lesson) {
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}
