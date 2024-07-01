package com.transport.transit.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableJpaRepositories(basePackages = { "com.transport.transit" })
@EntityScan(basePackages = { "com.transport.transit" })
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.transport.transit" })
public class ProductBootStarter {
	public static void main(String[] args) {
		SpringApplication.run(ProductBootStarter.class, args);
	}
	

}
