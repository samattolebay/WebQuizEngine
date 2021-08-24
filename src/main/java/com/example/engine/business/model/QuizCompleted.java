package com.example.engine.business.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quizzesCompleted")
public class QuizCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long alongId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Quiz quiz;

    private Long id;

    private final LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User solvedBy;

    public QuizCompleted() {
        this.completedAt = LocalDateTime.now();
    }

    public QuizCompleted(Quiz quiz, User solvedBy) {
        this.quiz = quiz;
        this.id = quiz.getId();
        this.solvedBy = solvedBy;
        this.completedAt = LocalDateTime.now();
    }

    public Long getAlongId() {
        return alongId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public User getSolvedBy() {
        return solvedBy;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setAlongId(Long alongId) {
        this.alongId = alongId;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSolvedBy(User solvedBy) {
        this.solvedBy = solvedBy;
    }

    @Override
    public String toString() {
        return "QuizCompleted{" +
                "alongId=" + alongId +
                ", id=" + quiz.getTitle() +
                ", completedAt=" + completedAt +
                ", solvedBy=" + solvedBy.getUsername() +
                '}';
    }

    public Long getId() {
        return id;
    }
}
