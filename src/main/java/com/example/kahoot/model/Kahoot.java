package com.example.kahoot.model;

import com.example.kahoot.model.question.Question;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "kahoots")
public class Kahoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean isPublic;
    private String coverImageURL;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "kahoot", cascade = CascadeType.PERSIST)
    private List<GameSession> gameSessions = new ArrayList<>();

    @OneToMany(mappedBy = "kahoot", cascade = CascadeType.PERSIST)
    private List<Question> questions;

    private Date createdAt;
}
