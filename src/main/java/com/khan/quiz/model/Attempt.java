package com.khan.quiz.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Quiz quiz;

    private int score;
    private LocalDateTime attemptedDate;
}
