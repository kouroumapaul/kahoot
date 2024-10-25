package com.example.kahoot.controller;

import com.example.kahoot.dto.gameSession.GameCreateDto;
import com.example.kahoot.dto.gameSession.GameSessionDto;
import com.example.kahoot.service.gameSession.GameSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
@Tag(name = "Game Session")
public class GameSessionController {

    private final GameSessionService gameSessionService;

    @Autowired
    public GameSessionController(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @Operation(
            summary = "Create a new game session",
            description = "Creates a new game session from a Kahoot quiz and returns the session details with a game pin"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Game session successfully created",
                    content = @Content(schema = @Schema(implementation = GameSessionDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data provided"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Referenced Kahoot quiz not found"
            )
    })
    @PostMapping
    public ResponseEntity<GameSessionDto> createGameSession(
            @Valid @RequestBody
            @Parameter(
                    description = "Game session creation data including the Kahoot quiz reference",
                    required = true
            )
            GameCreateDto gameCreateDto
    ) {
        GameSessionDto createdGameSession = gameSessionService.createGameSession(gameCreateDto);
        return new ResponseEntity<>(createdGameSession, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Find game session by game pin",
            description = "Retrieves an active game session using its unique game pin"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Game session found and returned",
                    content = @Content(schema = @Schema(implementation = GameSessionDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game session not found with the provided game pin"
            )
    })
    @GetMapping("/{gamePin}")
    public ResponseEntity<GameSessionDto> findGameSessionByGamePin(
            @Parameter(
                    description = "Unique pin of the game session to retrieve",
                    required = true,
                    example = "123456"
            )
            @PathVariable String gamePin
    ) {
        GameSessionDto gameSession = gameSessionService.findGameSessionByGamePin(gamePin);
        return new ResponseEntity<>(gameSession, HttpStatus.OK);
    }


}