package com.lingvopal.userservice.util;

import com.lingvopal.userservice.dto.response.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Signal;

@Slf4j
@Configuration
public class UserServiceHelper {

   /* private UserServiceHelper() {
        throw new IllegalStateException("Utility class");
    }*/
    public static void handleUserRetrievalResponse(String id, Signal<UserResponseDto> signal) {
        String idOrEmail = id == null ? "Unknown" : id;
        if (signal.isOnNext()) {
            log.info("User retrieved successfully: {}", signal.get());
        } else if (signal.isOnError()) {
            handleException(
                    String.format("Error retrieving user with id/email: %s",
                            idOrEmail), signal.getThrowable());
        }
    }

    public static void handleUserOperationResponse(Object userDto, Signal<UserResponseDto> signal, boolean isUpdate) {
        if (signal.isOnNext()) {
            log.info("User {} successfully: {}",
                    isUpdate ? "updated" : "created", signal.get());
        } else if (signal.isOnError()) {
            handleException(
                    String.format("Error %s user: %s",
                            isUpdate ? "updating" : "creating",
                            userDto), signal.getThrowable());
        }
    }

    private static void handleException(String message, Throwable throwable) {
        log.error(message, throwable);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                message, throwable);
    }

}