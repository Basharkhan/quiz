package com.khan.quiz.controller;

import com.khan.quiz.dto.QuestionDto;
import com.khan.quiz.dto.QuizDto;
import com.khan.quiz.repository.QuizRepository;
import com.khan.quiz.service.QuestionService;
import com.khan.quiz.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<QuestionDto> createQuiz(@Valid @RequestBody QuestionDto questionDto) {
        return ResponseEntity.ok(questionService.createQuestion(questionDto));
    }
}
