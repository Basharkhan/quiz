package com.khan.quiz.service;

import com.khan.quiz.dto.OptionDto;
import com.khan.quiz.dto.QuestionDto;
import com.khan.quiz.dto.UpdateQuestionDto;
import com.khan.quiz.exception.ResourceNotFoundException;
import com.khan.quiz.model.*;
import com.khan.quiz.repository.QuestionRepository;
import com.khan.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionDto createQuestion(QuestionDto questionDto) {
        Quiz quiz = quizRepository
                .findById(questionDto.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

        Question question = Question.builder()
                .text(questionDto.getText())
                .quiz(quiz)
                .build();

        if (questionDto.getOptions() != null && !questionDto.getOptions().isEmpty()) {
            List<Option> options = questionDto
                    .getOptions()
                    .stream()
                    .map(optionDto -> Option
                            .builder()
                            .text(optionDto.getText())
                            .correct(optionDto.isCorrect())
                            .question(question)
                            .build()
                    ).toList();
            question.setOptions(options);
        }

        Question savedQuestion = questionRepository.save(question);

        QuestionDto response = new QuestionDto();
        response.setId(savedQuestion.getId());
        response.setText(savedQuestion.getText());
        response.setQuizId(savedQuestion.getQuiz().getId());
        response.setOptions(
                savedQuestion.getOptions().stream()
                        .map(opt -> OptionDto
                                .builder()
                                .id(opt.getId())
                                .text(opt.getText())
                                .correct(opt.isCorrect())
                                .build()
                        )
                        .collect(Collectors.toList())
        );

        return response;
    }

    public UpdateQuestionDto updateQuestion(Long id, UpdateQuestionDto questionDto) {
        Question question =  questionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

        question.setText(questionDto.getText());

        List<Option> options = questionDto.getOptions().stream().map(opt -> Option
                .builder()
                .id(opt.getId())
                .text(opt.getText())
                .correct(opt.isCorrect())
                .question(question)
                .build()
        ).collect(Collectors.toList());

        question.getOptions().clear();
        question.getOptions().addAll(options);

        Question updatedQuestion = questionRepository.save(question);

        return UpdateQuestionDto
                .builder()
                .id(updatedQuestion.getId())
                .text(updatedQuestion.getText())
                .options(updatedQuestion.getOptions().stream().map(opt -> OptionDto
                        .builder()
                        .id(opt.getId())
                        .text(opt.getText())
                        .correct(opt.isCorrect())
                        .build()
                ).collect(Collectors.toList()))
                .build();
    }

    public List<QuestionDto> getQuestionsByQuizId(Long quizId) {
        return questionRepository
                .findByQuizId(quizId)
                .stream()
                .map(q -> {
                    QuestionDto dto = new QuestionDto();
                    dto.setText(q.getText());
                    dto.setId(q.getId());
                    dto.setQuizId(q.getQuiz().getId());
                    dto.setOptions(q.getOptions()
                            .stream()
                            .map(option -> OptionDto
                                    .builder()
                                    .id(option.getId())
                                    .text(option.getText())
                                    .correct(option.isCorrect())
                                    .build()
                            )
                            .collect(Collectors.toList())
                    );
                    return dto;
                }).collect(Collectors.toList());
    }

    public QuestionDto  getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setText(question.getText());
        questionDto.setQuizId(question.getQuiz().getId());
        questionDto.setOptions(question.getOptions().stream().map(option -> OptionDto
                .builder()
                .id(option.getId())
                .text(option.getText())
                .correct(option.isCorrect())
                .build()
        ).collect(Collectors.toList()));

        return questionDto;
    }

    public void deleteQuestionById(Long id) {
        questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

        questionRepository.deleteById(id);
    }
}
