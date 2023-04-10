package org.daemio.merch.config;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.security.role")
@Accessors(fluent = true)
@Getter
public class RoleConfig {

  @Value("${admin:ADMIN}")
  private String admin;

  @Value("${fan:FAN}")
  private String fan;

  @Value("${fan:STAN}")
  private String STAN;

  @Value("${vendor:VENDOR}")
  private String vendor;
}
