package com.backend.backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = {"com.backend.backend"})
@SpringBootApplication(scanBasePackages = {"com.backend.backend"})
@EnableTransactionManagement
@EntityScan(basePackages = "com.backend.backend.entities")
@EnableJpaRepositories(basePackages = "com.backend.backend.repositories", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
