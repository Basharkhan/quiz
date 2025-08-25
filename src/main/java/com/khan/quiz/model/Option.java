package com.khan.quiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Option {
    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private boolean isCorrect;

    @ManyToOne
    private Question question;
}
