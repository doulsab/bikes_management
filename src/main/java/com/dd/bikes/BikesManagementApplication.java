package com.dd.bikes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BikesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BikesManagementApplication.class, args);
	}

}
