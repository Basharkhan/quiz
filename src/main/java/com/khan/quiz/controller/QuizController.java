package com.khan.quiz.controller;

import com.khan.quiz.dto.ApiResponse;
import com.khan.quiz.dto.QuizDetailsDto;
import com.khan.quiz.dto.QuizDto;
import com.khan.quiz.dto.StudentQuizDetailsDto;
import com.khan.quiz.service.QuizService;
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
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<QuizDto>> createQuiz(@Valid @RequestBody QuizDto quizDto, Authentication authentication) {
        QuizDto savedQuiz = quizService.createQuiz(quizDto, authentication);

        ApiResponse<QuizDto> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Quiz created successfully",
                savedQuiz,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<QuizDetailsDto>> getQuizById(@PathVariable Long id) {
        QuizDetailsDto quizDetailsDto = quizService.getQuizById(id);

        ApiResponse<QuizDetailsDto> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Quiz retrieved successfully",
            quizDetailsDto,
            LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/attempt")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN') or hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<StudentQuizDetailsDto>> getQuizByIdForAttempt(@PathVariable Long id) {
        StudentQuizDetailsDto quizDetailsDto = quizService.getQuizByIdForAttempt(id);

        ApiResponse<StudentQuizDetailsDto> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Quiz retrieved successfully",
            quizDetailsDto,
            LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<QuizDto>> updateQuiz(@PathVariable Long id, @Valid @RequestBody QuizDto quizDto) {
        QuizDto updatedQuiz = quizService.updateQuiz(id, quizDto);

        ApiResponse<QuizDto> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Quiz updated successfully",
                updatedQuiz,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<QuizDto>>> getQuizzes() {
        List<QuizDto> quizDtos = quizService.getAllQuizzes();

        ApiResponse<List<QuizDto>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Quiz retrieved successfully",
                quizDtos,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteQuiz(@PathVariable Long id, Authentication authentication) {
        quizService.deleteQuizById(id, authentication);

        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Quiz deleted successfully",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }
}
