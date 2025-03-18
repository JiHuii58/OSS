package com.example.webapphkh.services;

import com.example.webapphkh.models.Quiz;
import com.example.webapphkh.models.Question;
import com.example.webapphkh.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;  
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public List<Quiz> getQuizzesByLessonId(Long lessonId) {
        return quizRepository.findByLesson_Id(lessonId);
    }

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    public ResponseEntity<String> submitQuiz(Long id, List<Question> submittedAnswers) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Không tìm thấy bài kiểm tra!");
        }

        Quiz quiz = quizOptional.get();
        int score = 0;

        List<Question> questions = quiz.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            if (i < submittedAnswers.size() &&
                    questions.get(i).getCorrectAnswer().equals(submittedAnswers.get(i).getSelectedAnswer())) {
                score++;
            }
        }

        quiz.setScore(score);
        quizRepository.save(quiz);
        return ResponseEntity.ok("Bạn đã đạt " + score + "/" + questions.size() + " điểm.");
    }
}
