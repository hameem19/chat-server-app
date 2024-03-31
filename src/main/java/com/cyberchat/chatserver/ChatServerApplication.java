package com.cyberchat.chatserver;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Chat REST APIs Documentation",
				description = "Chat REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Hameem Yousuf",
						email = "hameemyousuf@gmail.com"
				)
		)
)
public class ChatServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatServerApplication.class, args);
	}

}
