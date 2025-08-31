package com.khan.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerResponseDto {
    private Long questionId;
    private Long selectedOptionId;
    private boolean correct;
}