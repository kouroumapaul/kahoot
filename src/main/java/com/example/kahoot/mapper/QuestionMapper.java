package com.example.kahoot.mapper;

import com.example.kahoot.dto.answer.AnswerDto;
import com.example.kahoot.dto.question.QuestionSummaryDto;
import com.example.kahoot.model.question.MultiChoiceQuestion;
import com.example.kahoot.model.question.Question;
import com.example.kahoot.model.question.TrueFalseQuestion;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(target = "type", expression = "java(getQuestionType(question))")
    default QuestionSummaryDto toDto(Question question) {
        if (question instanceof MultiChoiceQuestion) {
            return toMultiChoiceQuestionDto((MultiChoiceQuestion) question);
        } else if (question instanceof TrueFalseQuestion) {
            return toTrueFalseQuestionDto((TrueFalseQuestion) question);
        }
        throw new IllegalArgumentException("The question type is not supported");
    }

    @Mapping(target = "answers", source = "answers")
    QuestionSummaryDto toMultiChoiceQuestionDto(MultiChoiceQuestion question);

    @Mapping(target = "isCorrect", source = "isCorrect")
    QuestionSummaryDto toTrueFalseQuestionDto(TrueFalseQuestion question);



    default String getQuestionType(Question question) {
        if (question instanceof MultiChoiceQuestion) {
            return "CHOIX_MULTIPLE";
        } else if (question instanceof TrueFalseQuestion) {
            return "VRAI_FAUX";
        }
        return "UNKNOWN";
    }
}