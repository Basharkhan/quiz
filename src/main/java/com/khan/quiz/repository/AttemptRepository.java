package com.khan.quiz.repository;

import com.khan.quiz.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    List<Attempt> findByStudentId(Long studentId);
    boolean existsByStudentIdAndQuizId(Long studentId, Long quizId);
    List<Attempt> findByQuizIdOrderByScoreDesc(Long quizId);
}
