package com.example.kahoot.model;

import jakarta.persistence.*;
import lombok.Data;
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

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Kahoot> kahoots;

    public void addKahoot(Kahoot kahoot) {
        kahoots.add(kahoot);
        kahoot.setUser(this);
    }

    public void removeKahoot(Kahoot kahoot) {
        kahoots.remove(kahoot);
        kahoot.setUser(null);
    }
}
