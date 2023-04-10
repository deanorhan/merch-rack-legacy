package org.daemio.merch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

@SpringBootTest(
    classes = MerchServiceApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "liquibase.enabled=false")
@ActiveProfiles("unit-test")
public class MerchProbeTest {

  @LocalServerPort private int port;

  @Test
  void testHealthProbes() throws Exception {
    given().port(port).when().get("/actuator/health/liveness").then().statusCode(200);

    given().port(port).when().get("/actuator/health/readiness").then().statusCode(200);

    given().port(port).when().get("/actuator/info").then().statusCode(200);
  }
}
