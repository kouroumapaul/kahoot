package com.example.kahoot.model.question;

import com.example.kahoot.exception.InvalidAnswerException;
import com.example.kahoot.model.Answer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CHOIX_MULTIPLE")
public class MultiChoiceQuestion extends Question {

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @Override
    public boolean checkAnswer(Object userAnswer) {
        if (!(userAnswer instanceof List)) {
            throw new InvalidAnswerException("Invalid answer format for multiple choice question");
        }
        List<Long> idsReponses = (List<Long>) userAnswer;
        List<Long> idsReponsesCorrectes = answers.stream()
                .filter(Answer::getIsCorrect)
                .map(Answer::getId)
                .toList();
        return idsReponses.size() == idsReponsesCorrectes.size() &&
                new HashSet<>(idsReponsesCorrectes).containsAll(idsReponses);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }
}
