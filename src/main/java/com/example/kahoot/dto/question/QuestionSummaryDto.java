package com.example.kahoot.dto.question;

import com.example.kahoot.dto.answer.AnswerDto;
import com.example.kahoot.dto.kahoot.KahootSummaryDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionSummaryDto {
    private String content;
    private Integer points;
    private Date createdAt;
    private Integer order;
    private String questionType;
    private List<AnswerDto> answers;
    private Boolean isCorrect;
    private KahootSummaryDto kahoot;
}