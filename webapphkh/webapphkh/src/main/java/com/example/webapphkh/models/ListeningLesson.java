package com.example.webapphkh.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "listening_lessons")
public class ListeningLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String videoUrl;

    @Column(columnDefinition = "TEXT")
    private String transcript;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ListeningQuestion> questions;

    // Constructors
    public ListeningLesson() {
    }

    public ListeningLesson(String title, String videoUrl, String transcript) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.transcript = transcript;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public List<ListeningQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ListeningQuestion> questions) {
        this.questions = questions;
    }
}
