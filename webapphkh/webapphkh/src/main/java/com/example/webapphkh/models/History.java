package com.example.webapphkh.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = true) 
    private Lesson lesson;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "score")
    private int score;

    @Column(name = "status")
    private String status;

    public History() {}

    public History(User user, Lesson lesson, Date timestamp, int score, String status) {
        this.user = user;
        this.lesson = lesson;
        this.timestamp = timestamp;
        this.score = score;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) { this.lesson = lesson; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
