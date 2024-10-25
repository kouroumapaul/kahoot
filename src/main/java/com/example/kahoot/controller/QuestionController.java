package com.example.kahoot.controller;

import com.example.kahoot.dto.question.QuestionDto;
import com.example.kahoot.dto.question.QuestionSummaryDto;
import com.example.kahoot.exception.ResourceNotFoundException;
import com.example.kahoot.service.question.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@Tag(name = "Question", description = "Endpoints for managing quiz questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    @Operation(
            summary = "Create a new question",
            description = "Creates a new question (multiple choice or true/false) for an existing Kahoot quiz"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Question successfully created",
                    content = @Content(schema = @Schema(implementation = QuestionSummaryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data provided or unsupported question type"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Referenced Kahoot does not exist"
            )
    })
    @PostMapping
    public ResponseEntity<QuestionSummaryDto> createQuestion(
            @Valid @RequestBody
            @Parameter(
                    description = "Question data including type (CHOIX_MULTIPLE or VRAI_FAUX), " +
                            "content, points, and kahootId",
                    required = true
            )
            QuestionDto questionDto
    ) {
        QuestionSummaryDto createdQuestion = questionService.createQuestion(questionDto);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Check answer for a question",
            description = "Validates if the provided answer is correct for the given question"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Answer checked successfully",
                    content = @Content(schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Question not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid answer format"
            )
    })
    @PostMapping("/{questionId}/check")
    public ResponseEntity<Boolean> checkAnswer(
            @PathVariable
            @Parameter(description = "ID of the question to check", required = true)
            Long questionId,

            @RequestBody
            @Parameter(description = "Answer to check", required = true)
            Object answer
    ) {
        System.out.println("INSTANCE OF BOOLEAN");
        System.out.println(answer.getClass().getName());
        boolean isCorrect = questionService.checkAnswer(questionId, answer);
        return ResponseEntity.ok(isCorrect);
    }
}