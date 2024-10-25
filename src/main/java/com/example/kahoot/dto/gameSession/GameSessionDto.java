package com.example.kahoot.dto.gameSession;

import com.example.kahoot.dto.kahoot.KahootDto;
import com.example.kahoot.dto.kahoot.KahootSummaryDto;
import com.example.kahoot.dto.player.PlayerDto;
import com.example.kahoot.dto.user.UserSummaryDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GameSessionDto {
    private Long id;
    private Date starAt;
    private Date endAt;
    private Date createdAt;
    private String gamePin;
    private String url;
    private KahootSummaryDto kahoot;
    private List<PlayerDto> players;
}
