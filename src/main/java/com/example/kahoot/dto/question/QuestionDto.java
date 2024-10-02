package com.example.kahoot.dto.question;

import com.example.kahoot.dto.answer.AnswerDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionDto {
    private String content;
    private Integer points;
    private Date createdAt;
    private Integer questionOrder;
    private String questionType;
    private List<AnswerDto> answers; // Pour les questions à choix multiple
    private Boolean isCorrect; // Pour les questions vrai/faux
    private Long kahootId;
}
