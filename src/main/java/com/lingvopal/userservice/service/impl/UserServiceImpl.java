package com.lingvopal.userservice.service.impl;

import static com.lingvopal.userservice.util.UserServiceHelper.handleUserOperationResponse;
import static com.lingvopal.userservice.util.UserServiceHelper.handleUserRetrievalResponse;

import com.lingvopal.userservice.dto.UserCreationDto;
import com.lingvopal.userservice.dto.UserUpdateDto;
import com.lingvopal.userservice.dto.mapper.UserMapper;
import com.lingvopal.userservice.dto.response.UserResponseDto;
import com.lingvopal.userservice.model.User;
import com.lingvopal.userservice.repository.UserRepository;
import com.lingvopal.userservice.service.UserService;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  @CachePut(cacheNames = "users")
  public Mono<UserResponseDto> createUser(UserCreationDto userDto) {
    return userRepository
        .save(userMapper.mapToUser(userDto))
        .map(userMapper::mapToUserResponseDto)
        .doOnEach(signal -> handleUserOperationResponse(userDto, signal, false));
  }

  @Override
  @CachePut(cacheNames = "users")
  public Mono<UserResponseDto> updateUser(String userId, UserUpdateDto userDto) {
    return userRepository
        .findById(userId)
        .flatMap(
            user -> {
              userMapper.mapToExistingUser(userDto, user);
              return userRepository.save(user);
            })
        .map(userMapper::mapToUserResponseDto)
        .doOnEach(signal -> handleUserOperationResponse(userDto, signal, true))
        .switchIfEmpty(
            Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
  }

  @Override
  @CacheEvict(cacheNames = "users")
  public Mono<Void> deleteUser(String userId) {
    return userRepository
        .existsById(userId)
        .flatMap(
            exist ->
                Boolean.TRUE.equals(exist)
                    ? userRepository.deleteById(userId)
                    : Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
  }

  @Override
  @Cacheable(cacheNames = "users")
  public Mono<UserResponseDto> getUserById(String userId) {
    return userRepository
        .findById(userId)
        .map(userMapper::mapToUserResponseDto)
        .doOnEach(signal -> handleUserRetrievalResponse(userId, signal));
  }






  @Override
  @Cacheable(cacheNames = "users")
  public Flux<UserResponseDto> getAllUsers() {
    return userRepository
        .findAll()
        .map(userMapper::mapToUserResponseDto)
        .doOnEach(signal -> handleUserRetrievalResponse(null, signal));
  }

  @Override
  @Cacheable(cacheNames = "users")
  public Mono<UserResponseDto> getUserByEmail(String email) {
    if (email == null || email.isEmpty()) {
      throw new IllegalArgumentException("Email should not be null or empty");
    }
    return userRepository
        .findByEmail(email)
        .map(userMapper::mapToUserResponseDto)
        .doOnEach(signal -> handleUserRetrievalResponse(email, signal));
  }

  @Override
  @Cacheable(cacheNames = "users")
  public Flux<UserResponseDto> getUsersByFirstName(String firstName) {
    if (firstName == null || firstName.isEmpty()) {
      throw new IllegalArgumentException("First name should not be null or empty");
    }
    return userRepository
        .findByFirstName(firstName)
        .take(20)
        .delayElements(Duration.ofMillis(500))
        .map(userMapper::mapToUserResponseDto)
        .doOnEach(signal -> handleUserRetrievalResponse(firstName, signal));
  }

  @Override

  public List<Mono<User>> createUsers(List<UserCreationDto> dtos) {
    return dtos.stream()
        .map(userMapper::mapToUser)
        .map(user -> {
          try {
            log.info("Saving user: {}", user);
            return userRepository.save(user);
          } catch (Exception ex) {
            log.error("Error saving user: {}", user, ex);
            return null;
          }
        })
        .filter(Objects::nonNull)
        .toList();
  }
}
