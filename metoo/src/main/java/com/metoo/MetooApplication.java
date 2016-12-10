package com.metoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MetooApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetooApplication.class, args);
	}
}
