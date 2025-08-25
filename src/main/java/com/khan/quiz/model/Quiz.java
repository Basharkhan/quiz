package com.khan.quiz.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Quiz {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    private User createdBy;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;
}
