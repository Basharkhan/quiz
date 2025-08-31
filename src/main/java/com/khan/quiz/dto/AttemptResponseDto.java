package com.khan.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttemptResponseDto {
    private Long attemptId;
    private Long userId;
    private Long quizId;
    private int score;
    private int totalQuestions;
    private LocalDateTime attemptedDate;
    private List<AnswerResponseDto> answers;
}
