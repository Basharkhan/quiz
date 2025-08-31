package com.khan.quiz.controller;

import com.khan.quiz.dto.AttemptRequestDto;
import com.khan.quiz.dto.AttemptResponseDto;
import com.khan.quiz.service.AttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class AttemptController {
    private final AttemptService attemptService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AttemptResponseDto> submitAttempt(
            @Valid @RequestBody AttemptRequestDto request) {
        return ResponseEntity.ok(attemptService.submitAttempt(request));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<List<AttemptResponseDto>> getAttemptsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(attemptService.getAttemptsByUser(userId));
    }
}
