package com.khan.quiz.service;

import com.khan.quiz.dto.QuizDto;
import com.khan.quiz.exception.ResourceNotFoundException;
import com.khan.quiz.model.Quiz;
import com.khan.quiz.model.User;
import com.khan.quiz.repository.QuizRepository;
import com.khan.quiz.repository.UserRepository;
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

    public QuizDto createQuiz(QuizDto quizDto) {
        User teacher = userRepository.findById(quizDto.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        Quiz quiz = Quiz.builder()
                .title(quizDto.getTitle())
                .description(quizDto.getDescription())
                .createdBy(teacher)
                .build();

        Quiz savedQuiz = quizRepository.save(quiz);

        quizDto.setId(quiz.getId());
        quizDto.setCreatedById(savedQuiz.getCreatedBy().getId());
        return quizDto;
    }

    public List<QuizDto> getAllQuizzes() {
        return quizRepository.findAll().stream().map(quiz -> {
            QuizDto quizDto = new QuizDto();
            quizDto.setTitle(quiz.getTitle());
            quizDto.setDescription(quiz.getDescription());
            quizDto.setCreatedById(quiz.getCreatedBy().getId());
            return quizDto;
        }).collect(Collectors.toList());
    }
}
