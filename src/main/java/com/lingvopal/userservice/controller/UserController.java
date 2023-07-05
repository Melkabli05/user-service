package com.lingvopal.userservice.controller;

import static com.lingvopal.userservice.util.MessageConstants.BAD_REQUEST;
import static com.lingvopal.userservice.util.MessageConstants.INTERNAL_SERVER_ERROR;
import static com.lingvopal.userservice.util.MessageConstants.USERS_NOT_FOUND;
import static com.lingvopal.userservice.util.MessageConstants.USERS_RETRIEVED;
import static com.lingvopal.userservice.util.MessageConstants.USER_ALREADY_EXISTS;
import static com.lingvopal.userservice.util.MessageConstants.USER_CREATED;
import static com.lingvopal.userservice.util.MessageConstants.USER_DELETED;
import static com.lingvopal.userservice.util.MessageConstants.USER_NOT_FOUND;
import static com.lingvopal.userservice.util.MessageConstants.USER_RETRIEVED;
import static com.lingvopal.userservice.util.MessageConstants.USER_UPDATED;

import com.lingvopal.userservice.dto.UserCreationDto;
import com.lingvopal.userservice.dto.UserUpdateDto;
import com.lingvopal.userservice.dto.response.UserResponseDto;
import com.lingvopal.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller",description = "User CRUD operations")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a User", description = "Create a new User", tags = {"User Controller"},responses = {
        @ApiResponse(responseCode = "200", description = USER_CREATED, content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST),
        @ApiResponse(responseCode = "409", description = USER_ALREADY_EXISTS),
        @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)}
    )
    public Mono<ResponseEntity<UserResponseDto>> createUser(@Valid @RequestBody UserCreationDto userDto) {
        return userService.createUser(userDto)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update a User", description = "Update an existing User", tags = {"User Controller"},responses = {
        @ApiResponse(responseCode = "200", description = USER_UPDATED, content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST),
        @ApiResponse(responseCode = "404", description = USER_NOT_FOUND),
        @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)}
    )
    public Mono<ResponseEntity<UserResponseDto>> updateUser(@PathVariable String userId,
        @Valid @RequestBody UserUpdateDto userDto) {
        return userService.updateUser(userId, userDto)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a User", description = "Delete an existing User", tags = {"User Controller"},responses = {
        @ApiResponse(responseCode = "200", description = USER_DELETED, content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST),
        @ApiResponse(responseCode = "404", description = USER_NOT_FOUND),
        @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)}
    )
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId)
            .thenReturn(ResponseEntity.ok().<Void>build())
            .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get a User", description = "Get an existing User", tags = {"User Controller"},responses = {
        @ApiResponse(responseCode = "200", description = USER_RETRIEVED, content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST),
        @ApiResponse(responseCode = "404", description = USER_NOT_FOUND),
        @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)}
    )
    public Mono<ResponseEntity<UserResponseDto>> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @GetMapping("/all/{firstName}")
    @Operation(summary = "Get all Users", description = "Get all Users by first name", tags = {"User Controller"},responses = {
        @ApiResponse(responseCode = "200", description = USERS_RETRIEVED, content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST),
        @ApiResponse(responseCode = "404", description = USERS_NOT_FOUND),
        @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)}
    )
    public Mono<ResponseEntity<List<UserResponseDto>>> getUsersByFirstName(@PathVariable String firstName) {
        return userService.getUsersByFirstName(firstName)
            .collectList()
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    private ResponseEntity<Void> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).build();
    }


}

