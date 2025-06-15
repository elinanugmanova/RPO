package ru.bmstu.rpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.bmstu.rpo"})
public class RpoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpoApplication.class, args);
	}

}
