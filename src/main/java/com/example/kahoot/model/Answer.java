package com.example.kahoot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Boolean isCorrect;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
