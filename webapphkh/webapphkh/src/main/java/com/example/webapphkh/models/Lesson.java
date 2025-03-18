package com.example.webapphkh.models;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String topic;

    @Column
    private String level;

    @Column(columnDefinition = "TEXT") // 🔥 Bỏ @Lob, chỉ dùng TEXT
    private String content;

    @Column(name = "audio_url") // 🔥 Đảm bảo ánh xạ cột đúng với PostgreSQL
    private String audioUrl;

    public Lesson() {}

    public Lesson(String title, String topic, String level, String content, String audioUrl) {
        this.title = title;
        this.topic = topic;
        this.level = level;
        this.content = content;
        this.audioUrl = audioUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
}
