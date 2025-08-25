package com.khan.quiz.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToOne
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;
}
