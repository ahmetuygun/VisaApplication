package com.krakozhia.visa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class VisaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisaApplication.class, args);
	}

}
