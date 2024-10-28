package com.example.kahoot.service.user;

import com.example.kahoot.annotation.LogExecutionTime;
import com.example.kahoot.dto.user.UserCreationDto;
import com.example.kahoot.dto.user.UserDto;
import com.example.kahoot.exception.ResourceAlreadyExistsException;
import com.example.kahoot.exception.ResourceNotFoundException;
import com.example.kahoot.mapper.UserMapper;
import com.example.kahoot.model.User;
import com.example.kahoot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
            throw new ResourceAlreadyExistsException("User", "username", userDto.getUsername());
        }
        User user = UserMapper.INSTANCE.toUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toUserDto(savedUser);
    }

    public UserDto updateUser(String username, UserCreationDto userUpdateDto) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User", "username", username);
        }

        existingUser.setUsername(userUpdateDto.getUsername());
        existingUser.setEmail(userUpdateDto.getEmail());

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.INSTANCE.toUserDto(updatedUser);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User", "username", username);
        }
        userRepository.delete(user);
    }

    @LogExecutionTime
    public Iterable<UserDto> findAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return userMapper.toUserDtos(users);
    }

    @Transactional
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken token) {
            String keycloakId = token.getName();

            return userRepository.findByKeycloakId(keycloakId)
                    .orElseGet(() -> createUserFromToken(token));
        }
        throw new IllegalStateException("User not authenticated");
    }

    @Transactional
    public User createUserFromToken(JwtAuthenticationToken token) {
        User user = new User();
        user.setKeycloakId(token.getName());
        user.setUsername(token.getTokenAttributes().get("preferred_username").toString());
        user.setEmail(token.getTokenAttributes().get("email").toString());
        return userRepository.save(user);
    }
}