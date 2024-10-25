package com.example.kahoot.dto.player;

import com.example.kahoot.dto.gameSession.GameSessionSummaryDto;
import com.example.kahoot.model.GameSession;
import lombok.Data;

import java.util.Date;

@Data
public class PlayerDto {
    private String nickname;
    private GameSessionSummaryDto gameSession;
}