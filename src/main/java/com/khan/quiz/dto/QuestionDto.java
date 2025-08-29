package com.khan.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    @NotBlank(message = "Text is required")
    private String text;

    @NotNull(message = "QuizId is required")
    private Long quizId;

    @NotEmpty(message = "At least one option is required")
    private List<OptionDto> options;
}
