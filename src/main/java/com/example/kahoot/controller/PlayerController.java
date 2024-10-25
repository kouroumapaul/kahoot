package com.example.kahoot.controller;

import com.example.kahoot.dto.player.PlayerCreationDto;
import com.example.kahoot.dto.player.PlayerDto;
import com.example.kahoot.dto.playerAnswer.PlayerAnswerDto;
import com.example.kahoot.exception.ApiErrorResponse;
import com.example.kahoot.model.PlayerAnswer;
import com.example.kahoot.service.player.PlayerService;
import com.example.kahoot.service.playerAnswer.PlayerAnswerService;
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
@RequestMapping(value = "/players", produces = "application/json")
@Tag(name = "Player")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerAnswerService playerAnswerService;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerAnswerService playerAnswerService) {
        this.playerService = playerService;
        this.playerAnswerService = playerAnswerService;
    }




    @Operation(
            summary = "Find a player by nickname"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Player found and returned",
                    content = @Content(schema = @Schema(implementation = PlayerDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player not found with the provided nickname",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping("/{nickname}")
    public ResponseEntity<PlayerDto> findPlayerByNickname(
            @Parameter(description = "Nickname of the player to find", required = true)
            @PathVariable String nickname
    ) {
        return playerService.findPlayerByNickname(nickname)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Join a game"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully joined the game",
                    content = @Content(schema = @Schema(implementation = PlayerDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data provided"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found"
            )
    })
    @PostMapping("join")
    public ResponseEntity<PlayerDto> joinGame(
            @Valid @RequestBody
            @Parameter(description = "Player data for joining the game", required = true)
            PlayerCreationDto playerCreationDto
    ) {
        PlayerDto joinedPlayer = playerService.joinGame(playerCreationDto);
        return new ResponseEntity<>(joinedPlayer, HttpStatus.OK);
    }

    @Operation(
            summary = "Submit an answer to a question",
            description = "Allows a player to submit their answer to a question"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Answer submitted successfully",
                    content = @Content(schema = @Schema(implementation = PlayerAnswer.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data provided",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player or question not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Player has already answered this question",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping("/{playerId}/answer")
    public ResponseEntity<PlayerAnswer> submitAnswer(
            @PathVariable
            @Parameter(description = "ID of the player submitting the answer", required = true)
            Long playerId,

            @Valid @RequestBody
            @Parameter(description = "Answer details", required = true)
            PlayerAnswerDto answerDto
    ) {
        // Set playerId from path into the DTO
        answerDto.setPlayerId(playerId);
        PlayerAnswer submittedAnswer = playerAnswerService.submitAnswer(answerDto);
        return ResponseEntity.ok(submittedAnswer);
    }
}