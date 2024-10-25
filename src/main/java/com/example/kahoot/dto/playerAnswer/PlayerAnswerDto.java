package com.example.kahoot.dto.playerAnswer;

import lombok.Data;
import java.util.List;

@Data
public class PlayerAnswerDto {
    private Long playerId;
    private Long questionId;
    private List<Long> selectedAnswerIds;
    private Integer answerTime;
}