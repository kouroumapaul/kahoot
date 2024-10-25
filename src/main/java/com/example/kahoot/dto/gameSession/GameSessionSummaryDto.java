package com.example.kahoot.dto.gameSession;

import lombok.Data;

@Data
public class GameSessionSummaryDto {
    private Long id;
    private String gamePin;
    private String url;
}
