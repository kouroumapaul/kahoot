package com.example.kahoot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
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


    @OneToMany(mappedBy = "gameSession")
    private List<Player> players = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "kahoot_id", nullable = false)
    private Kahoot kahoot;

    public void addPlayer(Player player) {
        players.add(player);
        player.setGameSession(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setGameSession(null);
    }
}
