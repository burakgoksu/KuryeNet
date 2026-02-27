package com.gp.KuryeNet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@ConfigurationPropertiesScan
@SpringBootApplication
public class KuryeNetApplication {

	public static void main(String[] args) {
		SpringApplication.run(KuryeNetApplication.class, args);
	}

}
