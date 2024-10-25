package com.example.kahoot.repository;

import com.example.kahoot.model.PlayerAnswer;
import com.example.kahoot.model.Player;
import com.example.kahoot.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerAnswerRepository extends JpaRepository<PlayerAnswer, Long> {
    boolean existsByPlayerAndQuestion(Player player, Question question);
}