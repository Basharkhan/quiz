package com.khan.quiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Attempt {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Quiz quiz;

    private int score;
    private LocalDateTime attemptedDate;
}
