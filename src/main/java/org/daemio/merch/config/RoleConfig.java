package org.daemio.merch.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.security.role")
@Getter
public class RoleConfig {

  @Value("${admin:ADMIN}")
  private String admin;

  @Value("${fan:FAN}")
  private String fan;

  @Value("${vendor:VENDOR}")
  private String vendor;
}
