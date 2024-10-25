package com.example.kahoot.service.playerAnswer;

import com.example.kahoot.dto.playerAnswer.PlayerAnswerDto;
import com.example.kahoot.exception.ResourceNotFoundException;
import com.example.kahoot.model.*;
import com.example.kahoot.model.question.Question;
import com.example.kahoot.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PlayerAnswerService {
    private final PlayerAnswerRepository playerAnswerRepository;
    private final PlayerRepository playerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public PlayerAnswerService(
            PlayerAnswerRepository playerAnswerRepository,
            PlayerRepository playerRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository) {
        this.playerAnswerRepository = playerAnswerRepository;
        this.playerRepository = playerRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public PlayerAnswer submitAnswer(PlayerAnswerDto answerDto) {
        Player player = playerRepository.findById(answerDto.getPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player", "id", answerDto.getPlayerId()));

        Question question = questionRepository.findById(answerDto.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", answerDto.getQuestionId()));

        // Vérifier si le joueur n'a pas déjà répondu à cette question
        if (playerAnswerRepository.existsByPlayerAndQuestion(player, question)) {
            throw new IllegalStateException("Player has already answered this question");
        }

        // Récupérer les réponses sélectionnées
        List<Answer> selectedAnswers = answerRepository.findAllById(answerDto.getSelectedAnswerIds());

        PlayerAnswer playerAnswer = new PlayerAnswer();
        playerAnswer.setPlayer(player);
        playerAnswer.setQuestion(question);
        playerAnswer.setSelectedAnswers(selectedAnswers);
        playerAnswer.setAnsweredAt(new Date());
        playerAnswer.setAnswerTime(answerDto.getAnswerTime());

        // Vérifier si la réponse est correcte
        boolean isCorrect = question.checkAnswer(answerDto.getSelectedAnswerIds());
        playerAnswer.setIsCorrect(isCorrect);

        // Calculer le score en fonction du temps de réponse et de l'exactitude
        int score = calculateScore(question.getPoints(), answerDto.getAnswerTime(), isCorrect);
        playerAnswer.setScoreEarned(score);

        // Mettre à jour le score du joueur
        player.setScore(player.getScore() + score);
        playerRepository.save(player);

        return playerAnswerRepository.save(playerAnswer);
    }

    private int calculateScore(int questionPoints, int answerTime, boolean isCorrect) {
        if (!isCorrect) return 0;

        // Le score diminue en fonction du temps de réponse
        // Par exemple : 100% du score si réponse en moins de 5 secondes
        // 50% du score si réponse entre 5 et 10 secondes
        // 25% du score si réponse en plus de 10 secondes
        if (answerTime <= 5) {
            return questionPoints;
        } else if (answerTime <= 10) {
            return questionPoints / 2;
        } else {
            return questionPoints / 4;
        }
    }
}