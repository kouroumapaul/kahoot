package com.example.kahoot.dto.question;

import com.example.kahoot.dto.answer.AnswerDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionDto {

    @NotNull(message = "Content is required")
    private String content;

    @Min(value = 1, message = "Points must be greater than 0")
    private Integer points;

    private Date createdAt;

    @Min(value = 1, message = "Question order must be greater than 0")
    private Integer questionOrder;

    @Pattern(regexp = "CHOIX_MULTIPLE|VRAI_FAUX", message = "Question type must be either 'CHOIX_MULTIPLE' or 'VRAI_FAUX'")
    private String questionType;

    private List<AnswerDto> answers; // Pour les questions à choix multiple
    private Boolean isCorrect; // Pour les questions vrai/faux

    @NotNull(message = "Kahoot ID is required")
    private Long kahootId;
}
