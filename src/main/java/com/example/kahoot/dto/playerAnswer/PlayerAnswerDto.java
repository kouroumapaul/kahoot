package com.example.kahoot.dto.playerAnswer;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class PlayerAnswerDto {
    @NotNull(message = "Player ID is required")
    private Long playerId;
    @NotNull(message = "Question ID is required")
    private Long questionId;
    private List<Long> selectedAnswerIds;
    private Boolean selectedAnswer;
    private Integer answerTime;

}