package com.example.kahoot.service.player;

import com.example.kahoot.dto.player.PlayerCreationDto;
import com.example.kahoot.dto.player.PlayerDto;
import com.example.kahoot.exception.ResourceAlreadyExistsException;
import com.example.kahoot.exception.ResourceNotFoundException;
import com.example.kahoot.mapper.PlayerMapper;
import com.example.kahoot.model.GameSession;
import com.example.kahoot.model.Player;
import com.example.kahoot.repository.GameSessionRepository;
import com.example.kahoot.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final GameSessionRepository gameSessionRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository,
                         PlayerMapper playerMapper,
                         GameSessionRepository gameSessionRepository) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.gameSessionRepository = gameSessionRepository;
    }

    public Optional<PlayerDto> findPlayerByNickname(String nickname) {
        Player player = playerRepository.findByNickname(nickname);
        return Optional.ofNullable(playerMapper.INSTANCE.toPlayerDto(player));
    }


    public PlayerDto joinGame(PlayerCreationDto playerCreationDto) {
        Optional<GameSession> gameSessionOpt = gameSessionRepository.findByGamePin(playerCreationDto.getGamePin());
        if (gameSessionOpt.isEmpty()) {
            throw new ResourceNotFoundException(
                    "GameSession",
                    "gamePin",
                    playerCreationDto.getGamePin()
            );
        }

        GameSession gameSession = gameSessionOpt.get();

        if (playerRepository.existsByNicknameAndGameSession(
                playerCreationDto.getNickname(),
                gameSession)) {
            throw new ResourceAlreadyExistsException(
                    "Player",
                    "nickname",
                    playerCreationDto.getNickname()
            );
        }

        Player player = new Player();
        player.setNickname(playerCreationDto.getNickname());
        player.setScore(0);
        gameSession.addPlayer(player);

        Player savedPlayer = playerRepository.save(player);
        return playerMapper.INSTANCE.toPlayerDto(savedPlayer);
    }
}