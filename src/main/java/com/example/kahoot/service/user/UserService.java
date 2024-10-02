package com.example.kahoot.service.user;

import com.example.kahoot.dto.user.UserCreationDto;
import com.example.kahoot.dto.user.UserDto;
import com.example.kahoot.exception.ResourceNotFoundException;
import com.example.kahoot.mapper.UserMapper;
import com.example.kahoot.model.User;
import com.example.kahoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private UserMapper userMapper = UserMapper.INSTANCE;


    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto findUserByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        return user.map(UserMapper.INSTANCE::toUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    public UserDto createUser(UserCreationDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = UserMapper.INSTANCE.toUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toUserDto(savedUser);
    }

    public Iterable<UserDto> findAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return userMapper.toUserDtos(users);
    }
}
