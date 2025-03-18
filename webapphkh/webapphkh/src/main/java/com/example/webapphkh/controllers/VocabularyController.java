package com.example.webapphkh.controllers;

import com.example.webapphkh.models.Vocabulary;
import com.example.webapphkh.services.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vocabulary")
public class VocabularyController {
    @Autowired
    private VocabularyService vocabularyService;

    @GetMapping
    public List<Vocabulary> getAllVocabulary() {
        return vocabularyService.getAllVocabulary();
    }

    @GetMapping("/topic/{topic}")
    public List<Vocabulary> getVocabularyByTopic(@PathVariable String topic) {
        return vocabularyService.getVocabularyByTopic(topic);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<Vocabulary>> getVocabularyByLesson(@PathVariable Long lessonId) {
        return vocabularyService.getVocabularyByLesson(lessonId);
    }

    @GetMapping("/unlearned")
    public List<Vocabulary> getUnlearnedWords() {
        return vocabularyService.getUnlearnedWords();
    }

    @PutMapping("/{id}/learned")
    public ResponseEntity<String> updateLearnedStatus(@PathVariable Long id, @RequestParam boolean isLearned) {
        return vocabularyService.updateLearnedStatus(id, isLearned);
    }

    @PostMapping
    public ResponseEntity<String> addVocabulary(@RequestBody Vocabulary vocabulary) {
        return vocabularyService.addVocabulary(vocabulary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVocabulary(@PathVariable Long id) {
        return vocabularyService.deleteVocabulary(id);
    }
}
