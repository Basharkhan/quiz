package com.khan.quiz.dto;

import com.khan.quiz.model.Quiz;
import com.khan.quiz.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttemptDto {
    private User student;
    private Quiz quiz;
    private int score;
}
