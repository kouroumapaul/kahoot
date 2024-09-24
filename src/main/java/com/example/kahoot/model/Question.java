package com.example.kahoot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Integer points;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "kahoot_id", nullable = false)
    private Kahoot kahoot;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    private List<Answer> answers;
}
