package com.example.kahoot.controller;

import com.example.kahoot.dto.gameSession.GameCreateDto;
import com.example.kahoot.dto.gameSession.GameSessionDto;
import com.example.kahoot.dto.kahoot.KahootCreateDto;
import com.example.kahoot.service.gameSession.GameSessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
public class GameSessionController {
    private final GameSessionService gameSessionService;

    @Autowired
    public GameSessionController(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @PostMapping
    public ResponseEntity<GameSessionDto> createGameSession(@Valid @RequestBody GameCreateDto gameCreateDto) {
        GameSessionDto createdGameSession = gameSessionService.createGameSession(gameCreateDto);
        return new ResponseEntity<>(createdGameSession, HttpStatus.CREATED);
    }

}