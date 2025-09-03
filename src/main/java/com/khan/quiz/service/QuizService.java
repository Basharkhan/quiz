package com.khan.quiz.service;

import com.khan.quiz.dto.*;
import com.khan.quiz.exception.ResourceNotFoundException;
import com.khan.quiz.model.Quiz;
import com.khan.quiz.model.Role;
import com.khan.quiz.model.User;
import com.khan.quiz.repository.QuizRepository;
import com.khan.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizDto createQuiz(QuizDto quizDto, Authentication authentication) {
        String username = authentication.getName();
        User teacher = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!teacher.getRole().equals(Role.TEACHER) && !teacher.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Only teacher and admin can create a quiz");
        }

        Quiz quiz = Quiz.builder()
                .title(quizDto.getTitle())
                .description(quizDto.getDescription())
                .createdBy(teacher)
                .build();

        Quiz savedQuiz = quizRepository.save(quiz);

        return QuizDto.builder()
                .id(savedQuiz.getId())
                .title(savedQuiz.getTitle())
                .description(savedQuiz.getDescription())
                .build();
    }

    public QuizDto updateQuiz(Long id, QuizDto quizDto) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        Quiz updatedQuiz = quizRepository.save(quiz);

        return QuizDto
                .builder()
                .id(updatedQuiz.getId())
                .title(updatedQuiz.getTitle())
                .description(updatedQuiz.getDescription())
                .build();
    }

    public QuizDetailsDto getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

        return QuizDetailsDto
                .builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .createdByEmail(quiz.getCreatedBy().getEmail())
                .questions(quiz.getQuestions().stream().map(q -> QuestionDto
                        .builder()
                        .id(q.getId())
                        .text(q.getText())
                        .options(q.getOptions().stream().map(option -> OptionDto
                                .builder()
                                .id(option.getId())
                                .text(option.getText())
                                .correct(option.isCorrect())
                                .build()).collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList()))
                .build();
    }

    public StudentQuizDetailsDto getQuizByIdForAttempt(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

        return StudentQuizDetailsDto
                .builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .createdByEmail(quiz.getCreatedBy().getEmail())
                .questions(quiz.getQuestions().stream().map(q -> StudentQuestionDto
                        .builder()
                        .id(q.getId())
                        .text(q.getText())
                        .options(q.getOptions().stream().map(option -> StudentOptionDto
                                .builder()
                                .id(option.getId())
                                .text(option.getText())
                                .build()).collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList()))
                .build();
    }

    public List<QuizDto> getAllQuizzes() {
        return quizRepository.findAll().stream().map(quiz -> {
            QuizDto quizDto = new QuizDto();
            quizDto.setId(quiz.getId());
            quizDto.setTitle(quiz.getTitle());
            quizDto.setDescription(quiz.getDescription());
            return quizDto;
        }).collect(Collectors.toList());
    }

    public void deleteQuizById(Long id, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getRole().equals(Role.TEACHER) && !user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Only teacher and admin can update a quiz");
        }

        quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + id));

        quizRepository.deleteById(id);
    }
}
