package com.wiley.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerForCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerForCustomerServiceApplication.class, args);
	}

}
