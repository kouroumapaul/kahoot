package com.example.kahoot.mapper;

import com.example.kahoot.dto.kahoot.KahootCreateDto;
import com.example.kahoot.dto.kahoot.KahootDto;
import com.example.kahoot.dto.kahoot.KahootSummaryDto;
import com.example.kahoot.dto.kahoot.KahootUpdateDto;
import com.example.kahoot.model.Kahoot;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class, QuestionMapper.class})
public interface KahootMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "questions", source = "questions")
    KahootDto toKahootDto(Kahoot kahoot);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    Kahoot toKahoot(KahootCreateDto kahootCreateDto);

    @IterableMapping(elementTargetType = KahootDto.class)
    Iterable<KahootSummaryDto> toKahootDtos(Iterable<Kahoot> kahoots);

}