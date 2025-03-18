package com.example.webapphkh.services;

import com.example.webapphkh.models.Lesson;
import com.example.webapphkh.models.Vocabulary;
import com.example.webapphkh.repositories.LessonRepository;
import com.example.webapphkh.repositories.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VocabularyService {
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private LessonRepository lessonRepository;


    public List<Vocabulary> getAllVocabulary() {
        return vocabularyRepository.findAll();
    }

    public List<Vocabulary> getVocabularyByTopic(String topic) {
        return vocabularyRepository.findByTopic(topic);
    }

    public ResponseEntity<List<Vocabulary>> getVocabularyByLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy bài học!"));
        List<Vocabulary> vocabularies = vocabularyRepository.findByLesson(lesson);
        return ResponseEntity.ok(vocabularies);
    }

    public List<Vocabulary> getUnlearnedWords() {
        return vocabularyRepository.findByIsLearned(false);
    }

    public ResponseEntity<String> updateLearnedStatus(Long id, boolean isLearned) {
        Optional<Vocabulary> optionalVocabulary = vocabularyRepository.findById(id);
        if (optionalVocabulary.isPresent()) {
            Vocabulary vocabulary = optionalVocabulary.get();
            vocabulary.setLearned(isLearned);
            vocabularyRepository.save(vocabulary);
            return ResponseEntity.ok("Cập nhật trạng thái học thành công!");
        }
        return ResponseEntity.badRequest().body("Không tìm thấy từ vựng cần cập nhật!");
    }

    public ResponseEntity<String> addVocabulary(Vocabulary vocabulary) {
        vocabularyRepository.save(vocabulary);
        return ResponseEntity.ok("Thêm từ vựng thành công!");
    }

    public ResponseEntity<String> deleteVocabulary(Long id) {
        if (vocabularyRepository.existsById(id)) {
            vocabularyRepository.deleteById(id);
            return ResponseEntity.ok("Xóa từ vựng thành công!");
        }
        return ResponseEntity.badRequest().body("Không tìm thấy từ vựng cần xóa!");
    }
}
