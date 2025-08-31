package com.khan.quiz.dto;

import com.khan.quiz.model.Quiz;
import com.khan.quiz.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttemptRequestDto {
    private Long userId;
    private Long quizId;
    private List<AnswerRequestDto> answers;
}
