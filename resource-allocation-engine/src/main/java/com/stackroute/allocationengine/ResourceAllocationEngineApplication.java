package com.stackroute.allocationengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ResourceAllocationEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceAllocationEngineApplication.class, args);
	}

}
