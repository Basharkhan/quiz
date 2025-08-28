package com.khan.quiz.model;

import jakarta.persistence.*;

@Entity(name = "quiz_option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    private boolean isCorrect;

    @ManyToOne
    private Question question;
}
