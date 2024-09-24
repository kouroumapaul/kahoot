package com.example.kahoot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "game_sessions")
@Data
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date starAt;

    private Date endAt;

    private Date createdAt;

    @Column(unique = true)
    private String gamePin;

    private String url;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "gameSession", cascade = CascadeType.PERSIST)
    private List<Player> players;

    @ManyToOne
    @JoinColumn(name = "kahoot_id", nullable = false)
    private Kahoot kahoot;
}
