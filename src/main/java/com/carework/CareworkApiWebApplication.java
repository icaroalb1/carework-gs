package com.carework;

import com.carework.config.CareworkProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(CareworkProperties.class)
public class CareworkApiWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareworkApiWebApplication.class, args);
	}

}
