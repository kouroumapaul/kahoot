package com.example.kahoot.mapper;

import com.example.kahoot.dto.player.PlayerDto;
import com.example.kahoot.model.Player;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerDto toPlayerDto(Player player);

    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    Player toPlayer(PlayerDto playerDto);

    @IterableMapping(elementTargetType = PlayerDto.class)
    Iterable<PlayerDto> toPlayerDtos(Iterable<Player> players);

    @IterableMapping(elementTargetType = Player.class)
    Iterable<Player> toPlayers(Iterable<PlayerDto> playerDtos);
}
