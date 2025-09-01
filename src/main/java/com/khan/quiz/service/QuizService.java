package com.khan.quiz.service;

import com.khan.quiz.dto.QuizDto;
import com.khan.quiz.exception.ResourceNotFoundException;
import com.khan.quiz.model.Quiz;
import com.khan.quiz.model.Role;
import com.khan.quiz.model.User;
import com.khan.quiz.repository.QuizRepository;
import com.khan.quiz.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

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

    public List<QuizDto> getAllQuizzes() {
        return quizRepository.findAll().stream().map(quiz -> {
            QuizDto quizDto = new QuizDto();
            quizDto.setId(quiz.getId());
            quizDto.setTitle(quiz.getTitle());
            quizDto.setDescription(quiz.getDescription());
            return quizDto;
        }).collect(Collectors.toList());
    }
}
