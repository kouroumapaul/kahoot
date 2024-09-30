package com.example.kahoot.mapper;

import com.example.kahoot.dto.gameSession.GameSessionDto;
import com.example.kahoot.model.GameSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameSessionMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "kahootId", source = "kahoot.id")
    GameSessionDto toDto(GameSession gameSession);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "kahoot", ignore = true)
    @Mapping(target = "players", ignore = true)
    GameSession toEntity(GameSessionDto gameSessionDto);
}

