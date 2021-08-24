package com.example.engine.business.services;

import com.example.engine.business.auth.IPrincipalFacade;
import com.example.engine.business.auth.UserPrincipal;
import com.example.engine.business.exceptions.QuizNotFoundException;
import com.example.engine.business.exceptions.UserDoesNotOwnQuizException;
import com.example.engine.business.model.*;
import com.example.engine.persistence.QuizRepository;
import com.example.engine.persistence.QuizSolvedRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserService userService;
    private final IPrincipalFacade principalFacade;
    private final QuizSolvedRepository quizSolvedRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, UserService userService, IPrincipalFacade principalFacade, QuizSolvedRepository quizSolvedRepository) {
        this.quizRepository = quizRepository;
        this.userService = userService;
        this.principalFacade = principalFacade;
        this.quizSolvedRepository = quizSolvedRepository;
    }

    @Override
    public Page<Quiz> findAllQuizzes(int page) {
        Pageable paging = PageRequest.of(page, PAGE_SIZE, Sort.by("id").descending());
        return quizRepository.findAll(paging);
    }

    @Override
    public Quiz findQuizById(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

    }

    @Override
    public Quiz saveQuiz(@NotNull Quiz quiz) {
        UserPrincipal principal = principalFacade.getPrincipal();
        return quizRepository.save(Quiz.builder()
                .title(quiz.getTitle())
                .text(quiz.getText())
                .options(quiz.getOptions())
                .answer(quiz.getAnswer())
                .createdBy(userService.findUserByEmail(principal.getUsername()))
                .build());
    }

    @Override
    public Feedback solveQuiz(long id, @NotNull Answer answer) {
        Quiz quiz = findQuizById(id);
        Feedback feedback = new Feedback(quiz.isAnswerCorrect(answer.getAnswer()));
        if (feedback.isSuccess()) {
            User user = userService.findUserByEmail(principalFacade.getPrincipal().getUsername());
            quizSolvedRepository.save(new QuizCompleted(quiz, user));
        }
        return feedback;
    }

    @Override
    public void deleteQuiz(long id) {
        Quiz quiz = findQuizById(id);
        UserPrincipal principal = principalFacade.getPrincipal();

        if (!quiz.getCreatedBy().getUsername().equals(principal.getUsername())) {
            throw new UserDoesNotOwnQuizException(principal.getUsername(), id);
        }

        quizRepository.delete(quiz);
    }

    @Override
    public Page<QuizCompleted> getCompletedQuizzes(int page) {
        Pageable paging = PageRequest.of(page, PAGE_SIZE, Sort.by("completedAt").descending());
        User user = userService.findUserByEmail(principalFacade.getPrincipal().getUsername());

        return quizSolvedRepository.findBySolvedBy(user, paging);
    }
}
