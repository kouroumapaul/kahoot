package com.example.kahoot.controller;

import com.example.kahoot.annotation.LogExecutionTime;
import com.example.kahoot.annotation.MonitorPerformance;
import com.example.kahoot.dto.user.UserCreationDto;
import com.example.kahoot.dto.user.UserDto;
import com.example.kahoot.exception.ApiErrorResponse;
import com.example.kahoot.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Find user by username",
            description = "Retrieves a specific user's details using their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found and returned",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found with the provided username",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{username}")
    public ResponseEntity<UserDto> findUserByUsername(
            @Parameter(description = "Username of the user to retrieve", required = true)
            @PathVariable String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @Operation(summary = "Create a new user",
            description = "Creates a new user account with the provided details")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User successfully created",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Username already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @Parameter(description = "User data for creation", required = true)
            @RequestBody UserCreationDto userCreationDto) {
        UserDto createdUser = userService.createUser(userCreationDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing user",
            description = "Updates the details of an existing user identified by their username")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully updated",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found with the provided username", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PutMapping("/{username}")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "Username of the user to update", required = true)
            @PathVariable String username,
            @Parameter(description = "Updated user data", required = true)
            @RequestBody UserCreationDto userUpdateDto) {
        UserDto updatedUser = userService.updateUser(username, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user",
            description = "Deletes a user account identified by their username")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User successfully deleted"
            ),
            @ApiResponse(responseCode = "404", description = "User not found with the provided username", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "Username of the user to delete", required = true)
            @PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List all users",
            description = "Retrieves a list of all registered users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of users successfully retrieved",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            )
    })
    @GetMapping
    @LogExecutionTime
    @MonitorPerformance
    public ResponseEntity<Iterable<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }
}