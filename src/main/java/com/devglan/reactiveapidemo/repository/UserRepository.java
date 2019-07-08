package com.devglan.reactiveapidemo.repository;

import com.devglan.reactiveapidemo.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
