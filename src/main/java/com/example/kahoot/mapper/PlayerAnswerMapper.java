package com.example.kahoot.mapper;

import com.example.kahoot.dto.playerAnswer.PlayerAnswerSummaryDto;
import com.example.kahoot.model.PlayerAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class, QuestionMapper.class})
public interface PlayerAnswerMapper {

    PlayerAnswerMapper INSTANCE = Mappers.getMapper(PlayerAnswerMapper.class);

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "question.content", target = "questionContent")
    @Mapping(source = "isCorrect", target = "isCorrect")
    @Mapping(source = "scoreEarned", target = "scoreEarned")
    @Mapping(source = "answerTime", target = "answerTime")
    @Mapping(source = "question.points", target = "points")
    PlayerAnswerSummaryDto toDto(PlayerAnswer playerAnswer);
}
