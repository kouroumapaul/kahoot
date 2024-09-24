package com.example.kahoot.dto.question;

import lombok.Data;

@Data
public class QuestionSummaryDto {
    private Long id;
    private String content;
    private String type;
}