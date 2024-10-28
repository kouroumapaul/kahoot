package com.example.kahoot.repository;

import com.example.kahoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    <Optional>User findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByKeycloakId(String keycloakId);

}
