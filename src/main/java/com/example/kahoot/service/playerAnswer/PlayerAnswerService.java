package com.example.kahoot.service.playerAnswer;

import com.example.kahoot.dto.playerAnswer.PlayerAnswerDto;
import com.example.kahoot.dto.playerAnswer.PlayerAnswerSummaryDto;
import com.example.kahoot.exception.InvalidAnswerException;
import com.example.kahoot.exception.ResourceNotFoundException;
import com.example.kahoot.mapper.PlayerAnswerMapper;
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
    private final PlayerAnswerMapper playerAnswerMapper;
    private final PlayerRepository playerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public PlayerAnswerService(
            PlayerAnswerRepository playerAnswerRepository,
            PlayerAnswerMapper playerAnswerMapper, PlayerRepository playerRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository) {
        this.playerAnswerRepository = playerAnswerRepository;
        this.playerAnswerMapper = playerAnswerMapper;
        this.playerRepository = playerRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public PlayerAnswerSummaryDto submitAnswer(PlayerAnswerDto answerDto) {
        boolean isCorrect = false;
        Player player = playerRepository.findById(answerDto.getPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player", "id", answerDto.getPlayerId()));

        Question question = questionRepository.findById(answerDto.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", answerDto.getQuestionId()));


        if (playerAnswerRepository.existsByPlayerAndQuestion(player, question)) {
            throw new InvalidAnswerException("Player has already answered this question");
        }

        PlayerAnswer playerAnswer = new PlayerAnswer();
        playerAnswer.setPlayer(player);
        playerAnswer.setQuestion(question);
        playerAnswer.setAnsweredAt(new Date());
        playerAnswer.setAnswerTime(answerDto.getAnswerTime());

        if(questionRepository.findById(answerDto.getQuestionId()).get().getQuestionType().equals("CHOIX_MULTIPLE")) {
            if(answerDto.getSelectedAnswerIds() == null || answerDto.getSelectedAnswerIds().isEmpty()) {
                throw new InvalidAnswerException("Selected answers are required for this question");
            }
            List<Answer> selectedAnswers = answerRepository.findAllById(answerDto.getSelectedAnswerIds());
            playerAnswer.setSelectedAnswers(selectedAnswers);
            isCorrect = question.checkAnswer(answerDto.getSelectedAnswerIds());
        }
        else {
            if(answerDto.getSelectedAnswer() == null) {
                throw new InvalidAnswerException("Selected answer (true/false) is required for this question");
            }
            Boolean selectedAnswer = answerDto.getSelectedAnswer();
            isCorrect = question.checkAnswer(selectedAnswer);
        }

        playerAnswer.setIsCorrect(isCorrect);



        int score = calculateScore(question.getPoints(), answerDto.getAnswerTime(), isCorrect);
        playerAnswer.setScoreEarned(score);

        player.setScore(player.getScore() + score);
        playerRepository.save(player);

        PlayerAnswer playerAnswerSaved =  playerAnswerRepository.save(playerAnswer);
        return playerAnswerMapper.INSTANCE.toDto(playerAnswerSaved);
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