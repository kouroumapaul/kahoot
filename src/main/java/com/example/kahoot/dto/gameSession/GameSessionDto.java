package com.example.kahoot.dto.gameSession;

import lombok.Data;

import java.util.Date;

@Data
public class GameSessionDto {
    private Long id;
    private Date starAt;
    private Date endAt;
    private Date createdAt;
    private String gamePin;
    private String url;
    private Long userId;
    private Long kahootId;
}
