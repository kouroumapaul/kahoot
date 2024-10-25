package com.example.kahoot.model;

import com.example.kahoot.model.question.Question;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "player_answers")
public class PlayerAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToMany
    @JoinTable(
            name = "player_answer_selected",
            joinColumns = @JoinColumn(name = "player_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private List<Answer> selectedAnswers;

    @Column(nullable = false)
    private Date answeredAt;

    private Integer answerTime;

    private Boolean isCorrect;

    private Integer scoreEarned;
}