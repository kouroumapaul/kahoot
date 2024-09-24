package com.example.kahoot.mapper;

import com.example.kahoot.dto.kahoot.KahootCreateDto;
import com.example.kahoot.dto.kahoot.KahootDto;
import com.example.kahoot.dto.kahoot.KahootSummaryDto;
import com.example.kahoot.dto.kahoot.KahootUpdateDto;
import com.example.kahoot.model.Kahoot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class, QuestionMapper.class})
public interface KahootMapper {

    KahootMapper INSTANCE = Mappers.getMapper(KahootMapper.class);
    KahootDto toKahootDto(Kahoot kahoot);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    Kahoot toKahoot(KahootCreateDto kahootCreateDto);

    @Mapping(target = "questionCount", expression = "java(kahoot.getQuestions().size())")
    KahootSummaryDto toKahootSummaryDto(Kahoot kahoot);

    void updateKahootFromDto(KahootUpdateDto kahootUpdateDto, @MappingTarget Kahoot kahoot);
}
