package com.example.engine.business.services;

import com.example.engine.business.model.Answer;
import com.example.engine.business.model.Feedback;
import com.example.engine.business.model.Quiz;
import com.example.engine.business.model.QuizCompleted;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Min;

public interface QuizService {
    int PAGE_SIZE = 10;

    Page<Quiz> findAllQuizzes(int page);

    Quiz findQuizById(long id);

    Quiz saveQuiz(Quiz quiz);

    Feedback solveQuiz(long id, Answer answer);

    void deleteQuiz(long id);

    Page<QuizCompleted> getCompletedQuizzes(@Min(0) int page);
}
