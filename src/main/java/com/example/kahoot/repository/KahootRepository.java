package com.example.kahoot.repository;

import com.example.kahoot.model.Kahoot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KahootRepository extends JpaRepository<Kahoot, Long> {
    List<Kahoot> findByCreatorId(Long creatorId);
    List<Kahoot> findByIsPublicTrue();
}