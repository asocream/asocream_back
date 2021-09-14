package com.yim.asocream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class AsocreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsocreamApplication.class, args);
	}
	@Bean
	public AuditorAware<String> auditorProvider()
	{
		return () -> Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
