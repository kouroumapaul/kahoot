package com.example.kahoot.mapper;

import com.example.kahoot.dto.gameSession.GameSessionDto;
import com.example.kahoot.model.GameSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GameSessionMapper {

    GameSessionMapper INSTANCE = Mappers.getMapper(GameSessionMapper.class);


    @Mapping(target = "kahoot", source = "kahoot")
    @Mapping(target = "players", source = "players")
    GameSessionDto toDto(GameSession gameSession);

}

