package org.daemio.merch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
    classes = MerchServiceApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = "liquibase.enabled=false")
@ActiveProfiles("unit-test")
public class MerchProbeTest {

  @Autowired private MockMvc mockMvc;

  @LocalServerPort private int port;

  @Test
  void testHealthProbes() throws Exception {
    this.mockMvc.perform(get("/actuator/health/liveness")).andExpect(status().isOk());

    this.mockMvc.perform(get("/actuator/health/readiness")).andExpect(status().isOk());

    this.mockMvc.perform(get("/actuator/info")).andExpect(status().isOk());
  }
}
