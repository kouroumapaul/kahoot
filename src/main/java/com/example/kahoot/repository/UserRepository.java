package com.example.kahoot.repository;

import com.example.kahoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    <Optional>User findByUsername(String username);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);



}
