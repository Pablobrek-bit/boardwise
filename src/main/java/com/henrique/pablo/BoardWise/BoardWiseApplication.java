package com.henrique.pablo.BoardWise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication(scanBasePackages = "com.henrique.pablo.BoardWise")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableJpaRepositories(basePackages = "com.henrique.pablo.BoardWise.infrastructure.persistence.repository")
@EntityScan(basePackages = "com.henrique.pablo.BoardWise.infrastructure.persistence.entity")
public class BoardWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardWiseApplication.class, args);
	}

}
