package com.saveme.ReportIncident_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReportIncidentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportIncidentServiceApplication.class, args);
	}

}
