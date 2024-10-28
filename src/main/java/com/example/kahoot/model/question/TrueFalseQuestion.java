package com.example.kahoot.model.question;

import com.example.kahoot.exception.InvalidAnswerException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("VRAI_FAUX")
public class TrueFalseQuestion extends Question {

    private Boolean isCorrect;

    @Override
    public boolean checkAnswer(Object userAnswer) {
        System.out.println("Instance of boolean: " + (userAnswer instanceof Boolean));
        System.out.println("User answer: " + userAnswer);
        if (!(userAnswer instanceof Boolean)) {
            throw new InvalidAnswerException("Invalid answer format for true/false question");
        }
        return userAnswer.equals(isCorrect);
    }

    @Override
    public String getQuestionType() {
        return "VRAI_FAUX";
    }
}
