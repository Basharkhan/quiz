package com.khan.quiz.service;

import com.khan.quiz.dto.QuizDto;
import com.khan.quiz.exception.ResourceNotFoundException;
import com.khan.quiz.model.Quiz;
import com.khan.quiz.model.User;
import com.khan.quiz.repository.QuizRepository;
import com.khan.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private QuizRepository quizRepository;
    private UserRepository userRepository;

    public QuizDto createQuiz(QuizDto quizDto) {
        User teacher = userRepository.findById(quizDto.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        Quiz quiz = Quiz.builder()
                .title(quizDto.getTitle())
                .description(quizDto.getDescription())
                .createdBy(teacher)
                .build();

        Quiz savedQuiz = quizRepository.save(quiz);

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
