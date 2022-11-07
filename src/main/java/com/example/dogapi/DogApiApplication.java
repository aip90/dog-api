package com.example.dogapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DogApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogApiApplication.class, args);
	}

}
