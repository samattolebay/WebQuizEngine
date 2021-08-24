package com.example.engine.presentation;

import com.example.engine.business.model.Answer;
import com.example.engine.business.model.Feedback;
import com.example.engine.business.model.Quiz;
import com.example.engine.business.model.QuizCompleted;
import com.example.engine.business.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public Page<Quiz> getQuizzes(@RequestParam(value = "page") @Min(0) int page) {
        return quizService.findAllQuizzes(page);
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable @Min(0) long id) {
        return quizService.findQuizById(id);
    }

    @PostMapping
    public Quiz saveQuiz(@RequestBody @Valid Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }

    @PostMapping("/{id}/solve")
    public Feedback solveQuiz(@PathVariable @Min(0) long id, @RequestBody @Valid Answer answer) {
        return quizService.solveQuiz(id, answer);
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable @Min(0) int id, HttpServletResponse response) {
        quizService.deleteQuiz(id);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @GetMapping("/completed")
    public Page<QuizCompleted> getCompletedQuizzes(@RequestParam(value = "page") @Min(0) int page) {
        return quizService.getCompletedQuizzes(page);
    }
}
