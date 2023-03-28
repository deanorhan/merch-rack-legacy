package org.daemio.merch;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@CucumberContextConfiguration
@SpringBootTest(classes = MerchServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class CucumberSpringConfiguration {
    
	@LocalServerPort
    private int port;

    @Before
	public void setUp() {
		log.info("Setting up RestAssured with port {}", port);
		RestAssured.port = port;
	}

	@After
	public void tearDown() {
		log.info("Reseting RestAssured");
		RestAssured.reset();
	}
}