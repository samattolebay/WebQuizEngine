package com.example.engine.persistence;

import com.example.engine.business.model.QuizCompleted;
import com.example.engine.business.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizSolvedRepository extends PagingAndSortingRepository<QuizCompleted, Long> {
    Page<QuizCompleted> findBySolvedBy(User solvedBy, Pageable pageable);
}
