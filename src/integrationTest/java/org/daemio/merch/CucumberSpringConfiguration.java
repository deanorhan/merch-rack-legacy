package org.daemio.merch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import org.daemio.merch.config.PersistenceConfig;
import org.daemio.merch.repository.MerchRepository;

@CucumberContextConfiguration
@SpringBootTest(
    classes = MerchServiceApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integ-test")
@Import(PersistenceConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class CucumberSpringConfiguration {

  @Autowired private MerchRepository merchRepository;
  @LocalServerPort private int port;

  @Before
  public void setUp() {
    log.info("Setting up RestAssured with port {}", port);
    RestAssured.port = port;
    merchRepository.deleteAll();
  }

  @After
  public void tearDown() {
    log.info("Reseting RestAssured");
    RestAssured.reset();
  }

  @AfterAll
  public static void finish() throws IOException {
    Files.deleteIfExists(Path.of("sqlite-integ.db"));
  }
}
