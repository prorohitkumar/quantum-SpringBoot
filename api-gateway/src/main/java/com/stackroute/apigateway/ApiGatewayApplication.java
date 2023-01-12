package com.stackroute.apigateway;

import com.stackroute.apigateway.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@CrossOrigin(origins = "*")
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean filter = new FilterRegistrationBean();
		filter.setFilter(new JwtFilter());
		filter.addUrlPatterns("/projectservice/api/v1/*");
		filter.addUrlPatterns("/profileservice/api/v1/userprofile/update");
		filter.addUrlPatterns("/profileservice/api/v1/userprofile/fetchuser");
		filter.addUrlPatterns("/profileservice/api/v1/userprofile/fetchusers");
		filter.addUrlPatterns("/profileservice/api/v1/userprofile/status");
		filter.addUrlPatterns("/searchservice/api/v1/*");
		filter.addUrlPatterns("/resourceallocationengine/api/v1/*");
		filter.addUrlPatterns("/nlpservice/api/v1/*");
		return filter;
	}

}
