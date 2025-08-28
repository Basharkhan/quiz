package com.khan.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizDto {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;

    @NotNull(message = "CreatedById is required")
    private Long createdById;
}
