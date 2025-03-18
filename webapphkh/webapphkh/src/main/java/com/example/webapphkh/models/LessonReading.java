package com.example.webapphkh.models;

import jakarta.persistence.*;

@Entity
@Table(name = "lesson_readings")
public class LessonReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // 🛠 Đảm bảo ánh xạ đúng kiểu TEXT
    private String content;

    public LessonReading() {}

    public LessonReading(Long lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
