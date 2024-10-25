package com.example.kahoot.controller;

import com.example.kahoot.dto.kahoot.*;
import com.example.kahoot.exception.ApiErrorResponse;
import com.example.kahoot.service.kahoot.KahootService;
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
@RequestMapping("/kahoots")
@Tag(name = "Kahoot")
public class KahootController {
    private final KahootService kahootService;

    @Autowired
    public KahootController(KahootService kahootService) {
        this.kahootService = kahootService;
    }

    @Operation(summary = "Create a new Kahoot")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Kahoot successfully created",
                    content = @Content(schema = @Schema(implementation = KahootDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<KahootDto> createKahoot(
            @Valid @RequestBody @Parameter(description = "Kahoot data to create", required = true) KahootCreateDto kahootCreateDto) {
        KahootDto createdKahoot = kahootService.createKahoot(kahootCreateDto);
        return new ResponseEntity<>(createdKahoot, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a Kahoot by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Kahoot found and returned",
                    content = @Content(schema = @Schema(implementation = KahootDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Kahoot not found with the provided ID",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping("/{kahootId}")
    public ResponseEntity<KahootDto> getKahoot(
            @Parameter(description = "ID of the Kahoot to retrieve", required = true) @PathVariable Long kahootId) {
        KahootDto kahoot = kahootService.getKahoot(kahootId);
        return new ResponseEntity<>(kahoot, HttpStatus.OK);
    }

    @Operation(summary = "List all Kahoots")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of Kahoots",
                    content = @Content(schema = @Schema(implementation = KahootSummaryDto.class))
            )
    })
    @GetMapping
    public ResponseEntity<Iterable<KahootSummaryDto>> getKahoots() {
        Iterable<KahootSummaryDto> kahoots = kahootService.findAllKahoots();
        return new ResponseEntity<>(kahoots, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing Kahoot")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Kahoot successfully updated",
                    content = @Content(schema = @Schema(implementation = KahootDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Kahoot not found with the provided ID",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PutMapping("/{kahootId}")
    public ResponseEntity<KahootDto> updateKahoot(
            @Parameter(description = "ID of the Kahoot to update", required = true) @PathVariable Long kahootId,
            @Valid @RequestBody @Parameter(description = "Updated Kahoot data", required = true) KahootCreateDto kahootUpdateDto) {
        KahootDto updatedKahoot = kahootService.updateKahoot(kahootId, kahootUpdateDto);
        return new ResponseEntity<>(updatedKahoot, HttpStatus.OK);
    }

    @Operation(summary = "Delete a Kahoot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Kahoot successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Kahoot not found with the provided ID",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @DeleteMapping("/{kahootId}")
    public ResponseEntity<Void> deleteKahoot(
            @Parameter(description = "ID of the Kahoot to delete", required = true) @PathVariable Long kahootId) {
        kahootService.deleteKahoot(kahootId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}