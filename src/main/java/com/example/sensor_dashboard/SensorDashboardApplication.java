package com.example.sensor_dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.sensor_dashboard", "com.example.sensor_dashboard.config"})
public class SensorDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorDashboardApplication.class, args);
	}

}
