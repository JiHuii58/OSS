package com.example.webapphkh.models;

import jakarta.persistence.*;

@Entity
@Table(name = "vocabularies")
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String meaning;

    @Column(name = "is_learned")
    private boolean isLearned;

    @Column(nullable = true)  // Thêm topic vào
    private String topic;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    public Vocabulary() {}

    public Vocabulary(String word, String meaning, boolean isLearned, String topic, Lesson lesson) {
        this.word = word;
        this.meaning = meaning;
        this.isLearned = isLearned;
        this.topic = topic;
        this.lesson = lesson;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public String getMeaning() { return meaning; }
    public void setMeaning(String meaning) { this.meaning = meaning; }

    public boolean isLearned() { return isLearned; }
    public void setLearned(boolean learned) { isLearned = learned; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }
}
