package com.stackroute.resourceallocate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ResourceAllocationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceAllocationServiceApplication.class, args);
	}

}
