package com.khan.quiz.service;

import com.khan.quiz.dto.*;
import com.khan.quiz.exception.QuizAlreadySubmittedException;
import com.khan.quiz.exception.ResourceNotFoundException;
import com.khan.quiz.model.*;
import com.khan.quiz.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttemptService {
    private final AttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public AttemptResponseDto submitAttempt(AttemptRequestDto request, Authentication authentication) {
        String username = authentication.getName();
        User student = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        boolean hasAlreadyAnswered = attemptRepository.existsByStudentIdAndQuizId(student.getId(), request.getQuizId());
        if (hasAlreadyAnswered) {
            throw new QuizAlreadySubmittedException("Attempt already submitted");
        }

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + request.getQuizId()));


        int score = 0;
        List<AnswerResponseDto> answerResponses = new ArrayList<>();

        for (AnswerRequestDto answer : request.getAnswers()) {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + answer.getQuestionId()));

            Option selectedOption = optionRepository.findById(answer.getSelectedOptionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Option not found with id: " + answer.getSelectedOptionId()));

            boolean correct = selectedOption.isCorrect();
            if (correct) score++;

            answerResponses.add(AnswerResponseDto
                    .builder()
                    .questionId(question.getId())
                    .selectedOptionId(selectedOption.getId())
                    .correct(correct)
                    .build()
            );
        }

        // save attempt
        Attempt attempt = Attempt
                .builder()
                .student(student)
                .quiz(quiz)
                .score(score)
                .attemptedDate(LocalDateTime.now())
                .build();
        Attempt savedAttempt = attemptRepository.save(attempt);

        // return dto
        return AttemptResponseDto
                .builder()
                .attemptId(savedAttempt.getId())
                .userId(student.getId())
                .quizId(quiz.getId())
                .score(score)
                .totalQuestions(request.getAnswers().size())
                .attemptedDate(LocalDateTime.now())
                .answers(answerResponses)
                .build();
    }

    public List<AttemptResponseDto> getAttemptsByUser(Long userId) {
        return attemptRepository.findByStudentId(userId)
                .stream()
                .map(attempt -> AttemptResponseDto.builder()
                        .attemptId((long) attempt.getId())
                        .userId(userId)
                        .quizId(attempt.getQuiz().getId())
                        .score(attempt.getScore())
                        .totalQuestions(attempt.getQuiz().getQuestions().size())
                        .attemptedDate(attempt.getAttemptedDate())
                        .answers(Collections.emptyList()) // can optionally fetch answers
                        .build())
                .toList();
    }

    public List<LeaderBoardEntryDto> getLeaderboard(Long quizId) {
        List<Attempt> attempts = attemptRepository.findByQuizIdOrderByScoreDesc(quizId);

        AtomicInteger rankCounter = new AtomicInteger(1);

        return attempts.stream()
                .map(attempt -> LeaderBoardEntryDto.builder()
                        .userId(attempt.getStudent().getId())
                        .studentName(attempt.getStudent().getFullName())
                        .score(attempt.getScore())
                        .attemptedDate(attempt.getAttemptedDate())
                        .rank(rankCounter.getAndIncrement())
                        .build())
                .collect(Collectors.toList());
    }
}
