package com.example.kahoot.repository;

import com.example.kahoot.model.Kahoot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KahootRepository extends JpaRepository<Kahoot, Long> {
    List<Kahoot> findByUserId(Long userId);
}