package com.khan.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private Long id;

    @NotBlank(message = "Text is required")
    private String text;

    @NotNull(message = "QuizId is required")
    private Long quizId;

    @NotEmpty(message = "At least one option is required")
    private List<OptionDto> options;
}
