package com.example.kahoot.model.question;

import com.example.kahoot.model.Kahoot;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type")
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Integer points;
    private Date createdAt;
    private Integer questionOrder;

    @ManyToOne
    @JoinColumn(name = "kahoot_id", nullable = false)
    private Kahoot kahoot;

    public abstract boolean checkAnswer(Object userAnswer);

    public abstract String getQuestionType();

}
