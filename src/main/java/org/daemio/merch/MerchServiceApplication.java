package org.daemio.merch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
public class MerchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchServiceApplication.class, args);
	}

}
