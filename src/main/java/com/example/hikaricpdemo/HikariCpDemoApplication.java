package com.example.hikaricpdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HikariCpDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HikariCpDemoApplication.class, args);
	}

}
