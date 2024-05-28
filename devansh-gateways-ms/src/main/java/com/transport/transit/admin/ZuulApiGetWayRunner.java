package com.transport.transit.admin;

import com.transport.transit.admin.filters.ErrorFilter;
import com.transport.transit.admin.filters.PostFilter;
import com.transport.transit.admin.filters.PreFilter;
import com.transport.transit.admin.filters.RouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulApiGetWayRunner {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApiGetWayRunner.class, args);
		System.out.println("Zuul server is running...");
	}

	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}

	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}

	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}

	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}
}
