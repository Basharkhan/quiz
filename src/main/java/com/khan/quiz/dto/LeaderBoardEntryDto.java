package com.khan.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaderBoardEntryDto {
    private Long userId;
    private String studentName;
    private int score;
    private LocalDateTime attemptedDate;
    private int rank;
}
