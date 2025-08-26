package com.khan.quiz.dto;

import lombok.Data;

@Data
public class QuizDto {
    private String title;
    private String description;
    private Long createdById;
}
