package com.henrique.pablo.BoardWise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication(scanBasePackages = "com.henrique.pablo.BoardWise")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class BoardWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardWiseApplication.class, args);
	}

}
