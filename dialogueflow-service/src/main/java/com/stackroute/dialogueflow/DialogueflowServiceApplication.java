package com.stackroute.dialogueflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class DialogueflowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DialogueflowServiceApplication.class, args);
	}

}
