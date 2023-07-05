package com.lingvopal.userservice.service;

import com.lingvopal.userservice.dto.UserCreationDto;
import com.lingvopal.userservice.dto.UserUpdateDto;
import com.lingvopal.userservice.dto.response.UserResponseDto;
import com.lingvopal.userservice.model.User;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponseDto> createUser(UserCreationDto userDto);
    Mono<UserResponseDto> updateUser(String userId, UserUpdateDto userDto);
    Mono<Void> deleteUser(String userId);
    Mono<UserResponseDto> getUserById(String userId);
    Flux<UserResponseDto> getAllUsers();

    Mono<UserResponseDto> getUserByEmail(String email);
    Flux<UserResponseDto> getUsersByFirstName(String firstName);

    List<Mono<User>> createUsers(List<UserCreationDto> userCreationDtos);
}
