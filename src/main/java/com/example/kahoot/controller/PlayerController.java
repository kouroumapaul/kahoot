package com.example.kahoot.controller;

import com.example.kahoot.dto.player.PlayerCreationDto;
import com.example.kahoot.dto.player.PlayerDto;
import com.example.kahoot.service.player.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/players", produces = "application/json")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        PlayerDto createdPlayer = playerService.createPlayer(playerDto);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<PlayerDto> findPlayerByNickname(@PathVariable String nickname) {
        return playerService.findPlayerByNickname(nickname)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("join")
    public ResponseEntity<PlayerDto> joinGame(@Valid @RequestBody PlayerCreationDto playerCreationDto) {
        PlayerDto joinedPlayer = playerService.joinGame(playerCreationDto);
        return new ResponseEntity<>(joinedPlayer, HttpStatus.OK);
    }
}
