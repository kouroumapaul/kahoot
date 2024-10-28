package com.example.kahoot.dto.playerAnswer;

import lombok.Data;

@Data
public class PlayerAnswerSummaryDto {
    private Long id;
    private Long playerId;
    private Long questionId;
    private String questionContent;
    private Boolean isCorrect;
    private Integer scoreEarned;
    private Integer answerTime;
    private Integer points;
    private Integer totalPoints;
}
