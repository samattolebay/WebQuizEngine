package com.example.engine.business.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotBlank(message = "Title is required!")
    private String title;

    @NotBlank(message = "Text is required!")
    private String text;

    @NotEmpty(message = "There must be at least 2 options!")
    @Size(min = 2, message = "There must be at least 2 options!")
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User createdBy;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<QuizCompleted> quizzesSolved;

    public Quiz() {
        this(null, null, null, null, null, null);
    }

    public Quiz(String title, String text, String[] options, int[] answer, User createdBy, List<QuizCompleted> quizzesSolved) {
        this.title = Optional.ofNullable(title).isPresent() ? title : "";
        this.text = Optional.ofNullable(text).isPresent() ? text : "";
        this.options = Optional.ofNullable(options).isPresent() ? options : new String[0];
        this.answer = Optional.ofNullable(answer).isPresent() ? answer : new int[0];
        this.createdBy = createdBy;
        this.quizzesSolved = quizzesSolved;
    }

    public Quiz(QuizBuilder quizBuilder) {
        this.title = quizBuilder.title;
        this.text = quizBuilder.text;
        this.options = quizBuilder.options;
        this.answer = quizBuilder.answer;
        this.createdBy = quizBuilder.createdBy;
        this.quizzesSolved = quizBuilder.quizzesSolved;
    }

    public static QuizBuilder builder() {
        return new QuizBuilder();
    }

    public static class QuizBuilder {
        private long id;
        private String title;
        private String text;
        private String[] options;
        private int[] answer;
        private User createdBy;
        public List<QuizCompleted> quizzesSolved;

        public QuizBuilder() {

        }

        public QuizBuilder title(String title) {
            this.title = title;
            return this;
        }

        public QuizBuilder text(String text) {
            this.text = text;
            return this;
        }

        public QuizBuilder options(String[] options) {
            this.options = options;
            return this;
        }

        public QuizBuilder answer(int[] answer) {
            this.answer = answer;
            return this;
        }

        public QuizBuilder createdBy(User createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public QuizBuilder quizzesSolved(List<QuizCompleted> quizzesSolved) {
            this.quizzesSolved = quizzesSolved;
            return this;
        }

        public Quiz build() {
            return new Quiz(this);
        }
    }

    @Override
    public String toString() {
        return "[Title: " + title
                + "\nText: " + text
                + "\nOptions: " + Arrays.toString(options)
                + "\nAnswer: " + Arrays.toString(answer);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isAnswerCorrect(int[] answerToCheck) {
        return Arrays.equals(this.answer, answerToCheck);
    }
}
