package com.vibely;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vibely.repository.UserRepository;

@SpringBootApplication
public class VibelyApplication {
	public static void main(String[] args) {
		SpringApplication.run(VibelyApplication.class, args);
	}

	@Bean
	CommandLineRunner test(UserRepository repo){
		return args -> {
			System.out.println("successful");
			System.out.println("Total users:" + repo.count());
	};
}
}
