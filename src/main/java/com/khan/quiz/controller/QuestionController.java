package com.khan.quiz.controller;

import com.khan.quiz.dto.ApiResponse;
import com.khan.quiz.dto.QuestionDto;
import com.khan.quiz.dto.UpdateQuestionDto;
import com.khan.quiz.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<ApiResponse<QuestionDto>> createQuestion(@Valid @RequestBody QuestionDto questionDto) {
        QuestionDto savedQuestion =  questionService.createQuestion(questionDto);

        ApiResponse<QuestionDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Question created successfully",
                savedQuestion,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UpdateQuestionDto>> updateQuestion(@PathVariable Long id,
                                                                         @Valid @RequestBody UpdateQuestionDto questionDto) {
        UpdateQuestionDto updateQuestion =  questionService.updateQuestion(id, questionDto);

        ApiResponse<UpdateQuestionDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Question updated successfully",
                updateQuestion,
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<QuestionDto>> getQuestionById(@PathVariable Long id) {
        QuestionDto question = questionService.getQuestionById(id);

        ApiResponse<QuestionDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Questions retrieved successfully",
                question,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteQuestionById(@PathVariable Long id) {
        questionService.deleteQuestionById(id);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Questions deleted successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
