package com.karatesan.WebAppApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableWebSecurity(debug = true)
public class WebAppApiApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(WebAppApiApplication.class);
		app.run(args);
		//SpringApplication.run(WebAppApiApplication.class, args);
	}

}
