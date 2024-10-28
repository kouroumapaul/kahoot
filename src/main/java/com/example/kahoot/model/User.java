package com.example.kahoot.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(name = "keycloak_id", nullable = false, unique = true)
    private String keycloakId;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Kahoot> kahoots = new ArrayList<>();

    public void addKahoot(Kahoot kahoot) {
        kahoots.add(kahoot);
        kahoot.setUser(this);
    }

    public void removeKahoot(Kahoot kahoot) {
        kahoots.remove(kahoot);
        kahoot.setUser(null);
    }
}