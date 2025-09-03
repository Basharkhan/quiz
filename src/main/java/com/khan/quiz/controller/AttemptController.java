package com.khan.quiz.controller;

import com.khan.quiz.dto.ApiResponse;
import com.khan.quiz.dto.AttemptRequestDto;
import com.khan.quiz.dto.AttemptResponseDto;
import com.khan.quiz.dto.LeaderBoardEntryDto;
import com.khan.quiz.service.AttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class AttemptController {
    private final AttemptService attemptService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<AttemptResponseDto> submitAttempt(
            @Valid @RequestBody AttemptRequestDto request, Authentication authentication) {
        return ResponseEntity.ok(attemptService.submitAttempt(request, authentication));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<List<AttemptResponseDto>> getAttemptsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(attemptService.getAttemptsByUser(userId));
    }

    @GetMapping("/{quizId}/leaderboard")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<LeaderBoardEntryDto>>> getLeaderboard(@PathVariable Long quizId) {
        List<LeaderBoardEntryDto> leaderboard = attemptService.getLeaderboard(quizId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Leaderboard retrieved successfully", leaderboard, LocalDateTime.now())
        );
    }

}
