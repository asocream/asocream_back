package com.yim.asocream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AsocreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsocreamApplication.class, args);
	}

}
