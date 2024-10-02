package com.example.kahoot.service.gameSession;

import com.example.kahoot.dto.gameSession.GameCreateDto;
import com.example.kahoot.dto.gameSession.GameSessionDto;
import com.example.kahoot.mapper.GameSessionMapper;
import com.example.kahoot.model.GameSession;
import com.example.kahoot.model.Kahoot;
import com.example.kahoot.model.User;
import com.example.kahoot.repository.GameSessionRepository;
import com.example.kahoot.repository.KahootRepository;
import com.example.kahoot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class GameSessionService {

    private final GameSessionRepository gameSessionRepository;
    private final UserRepository userRepository;
    private final KahootRepository kahootRepository;
    private final GameSessionMapper gameSessionMapper;

    @Autowired
    public GameSessionService(GameSessionRepository gameSessionRepository,
                              UserRepository userRepository,
                              KahootRepository kahootRepository,
                              GameSessionMapper gameSessionMapper) {
        this.gameSessionRepository = gameSessionRepository;
        this.userRepository = userRepository;
        this.kahootRepository = kahootRepository;
        this.gameSessionMapper = gameSessionMapper;
    }

    @Transactional
    public GameSessionDto createGameSession(GameCreateDto gameCreateDto) {
        User user = userRepository.findById(gameCreateDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Kahoot kahoot = kahootRepository.findById(gameCreateDto.getKahootId())
                .orElseThrow(() -> new IllegalArgumentException("Kahoot not found"));

        GameSession gameSession = new GameSession();
        gameSession.setStarAt(new Date());
        gameSession.setCreatedAt(new Date());
        gameSession.setGamePin(generateUniqueGamePin());
        gameSession.setUser(user);
        gameSession.setKahoot(kahoot);

        gameSessionRepository.save(gameSession);
        return gameSessionMapper.INSTANCE.toDto(gameSession);
    }

    public GameSessionDto findGameSessionByGamePin(String gamePin) {
        GameSession gameSession = gameSessionRepository.findByGamePin(gamePin)
                .orElseThrow(() -> new IllegalArgumentException("Game session not found"));
        return gameSessionMapper.INSTANCE.toDto(gameSession);
    }

    private String generateUniqueGamePin() {
        Random random = new Random();
        String gamePin;
        do {
            gamePin = String.format("%06d", random.nextInt(1000000));
        } while (gameSessionRepository.findByGamePin(gamePin).isPresent());
        return gamePin;
    }
}
