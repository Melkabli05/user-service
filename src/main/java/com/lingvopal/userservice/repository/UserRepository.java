package com.lingvopal.userservice.repository;

import com.lingvopal.userservice.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository @Component
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByEmail(String email);

    Flux<User> findByFirstName(String firstName);
}
