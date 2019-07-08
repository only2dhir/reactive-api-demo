package com.devglan.reactiveapidemo;

import com.devglan.reactiveapidemo.model.User;
import com.devglan.reactiveapidemo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveApiDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository){
		return args -> {
			Flux<User> users = Flux.just(
					new User("Dhiraj", 23, 3456),
					new User("Mike", 34, 3421),
					new User("Hary", 21, 8974))
					.flatMap(userRepository :: save);

			users.thenMany(userRepository.findAll())
					.subscribe(System.out :: println);
		};
	}

}
