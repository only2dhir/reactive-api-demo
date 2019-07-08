package com.devglan.reactiveapidemo.controller;

import com.devglan.reactiveapidemo.model.User;
import com.devglan.reactiveapidemo.model.UserEvent;
import com.devglan.reactiveapidemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Mono<ResponseEntity<User>> create(@RequestBody User user){
        return userRepository.save(user)
                .map(savedUser -> ResponseEntity.ok(savedUser));
    }

    @GetMapping
    public Flux<User> listUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable String userId){
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(user))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable String userId, @RequestBody User user){
        return userRepository.findById(userId)
               .flatMap(dbUser -> {
                   dbUser.setAge(user.getAge());
                   dbUser.setSalary(user.getSalary());
                   return userRepository.save(dbUser);
               })
                .map(updatedUser -> ResponseEntity.ok(updatedUser))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable String userId){
        return userRepository.findById(userId)
                .flatMap(existingUser ->
                    userRepository.delete(existingUser)
                            .then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserEvent> emitEvents(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(val -> new UserEvent("" + val, "Devglan User Event"));
    }


}
