package com.example.kahoot.mapper;

import com.example.kahoot.dto.user.UserCreationDto;
import com.example.kahoot.dto.user.UserDto;
import com.example.kahoot.model.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    User toUser(UserCreationDto userCreationDto);

    @IterableMapping(elementTargetType = UserDto.class)
    Iterable<UserDto> toUserDtos(Iterable<User> users);

    @IterableMapping(elementTargetType = User.class)
    Iterable<User> toUsers(Iterable<UserDto> userDtos);
}
