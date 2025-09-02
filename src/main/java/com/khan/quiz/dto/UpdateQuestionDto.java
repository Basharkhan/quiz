package com.khan.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateQuestionDto {
    private Long id;

    @NotBlank(message = "Text is required")
    private String text;

    @NotEmpty(message = "At least one option is required")
    private List<OptionDto> options;
}
