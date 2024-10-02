package com.example.kahoot.service.player;


import com.example.kahoot.dto.gameSession.GameSessionDto;
import com.example.kahoot.dto.player.PlayerCreationDto;
import com.example.kahoot.dto.player.PlayerDto;
import com.example.kahoot.mapper.GameSessionMapper;
import com.example.kahoot.mapper.PlayerMapper;
import com.example.kahoot.model.GameSession;
import com.example.kahoot.model.Player;
import com.example.kahoot.repository.GameSessionRepository;
import com.example.kahoot.repository.PlayerRepository;
import com.example.kahoot.service.gameSession.GameSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final GameSessionService gameSessionService;
    private final PlayerMapper playerMapper;
    private final GameSessionMapper gameSessionMapper;
    private final GameSessionRepository gameSessionRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, GameSessionService gameSessionService, PlayerMapper playerMapper, GameSessionMapper gameSessionMapper, GameSessionRepository gameSessionRepository) {
        this.playerRepository = playerRepository;
        this.gameSessionService = gameSessionService;
        this.playerMapper = playerMapper;
        this.gameSessionMapper = gameSessionMapper;
        this.gameSessionRepository = gameSessionRepository;
    }

    public PlayerDto createPlayer(PlayerDto playerDto) {
        Player player = playerMapper.toPlayer(playerDto);
        player.setGameSession(null); // player will join game session later
        Player savedPlayer = playerRepository.save(player);

        return playerMapper.toPlayerDto(savedPlayer);
    }

    public Optional<PlayerDto> findPlayerByNickname(String nickname) {
        Player player = playerRepository.findByNickname(nickname);
        return Optional.ofNullable(playerMapper.toPlayerDto(player));
    }

    public void deletePlayerByNickname(String nickname) {
        Player player = playerRepository.findByNickname(nickname);
        playerRepository.delete(player);
    }

    public PlayerDto joinGame(PlayerCreationDto playerCreationDto) {

        Optional<GameSession> gameSessionDto = gameSessionRepository.findByGamePin(playerCreationDto.getGamePin());

        if (gameSessionDto.isEmpty()) {
            throw new IllegalArgumentException("Game session with game pin " + playerCreationDto.getGamePin() + " not found");
        }

        Player player = new Player();
        player.setNickname(playerCreationDto.getNickname());

        gameSessionDto.get().addPlayer(player);

        Player savedPlayer = playerRepository.save(player);
        return playerMapper.toPlayerDto(savedPlayer);
    }



}
