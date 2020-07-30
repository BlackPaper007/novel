package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootConfiguration
@MapperScan("com.dao")
@SpringBootApplication
public class NovelApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovelApplication.class, args);
	}

}
