package com.khan.quiz.controller;

import com.khan.quiz.dto.ApiResponse;
import com.khan.quiz.dto.QuestionDto;
import com.khan.quiz.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<QuestionDto>> createQuiz(@Valid @RequestBody QuestionDto questionDto) {
        QuestionDto savedQuestion =  questionService.createQuestion(questionDto);

        ApiResponse<QuestionDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Question created successfully",
                savedQuestion,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/quiz/{quizId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<QuestionDto>>> getQuestionsByQuizId(@PathVariable Long quizId) {
        List<QuestionDto> questions = questionService.getQuestionsByQuizId(quizId);

        ApiResponse<List<QuestionDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Questions found",
                questions,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
