package com.example.kahoot.model.question;

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
        if (!(userAnswer instanceof Boolean)) {
            throw new IllegalArgumentException("La réponse doit être un booléen pour les questions vrai/faux");
        }
        return userAnswer == isCorrect;
    }
}
