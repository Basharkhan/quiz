package com.khan.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDetailsDto {
    private Long id;
    private String title;
    private String description;
    String createdByEmail;
    List<QuestionDto> questions;
}
