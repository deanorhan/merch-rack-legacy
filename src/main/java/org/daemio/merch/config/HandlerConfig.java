package org.daemio.merch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

import org.daemio.merch.filter.JWTAuthorizationFilter;

@Configuration
public class HandlerConfig {

  @Bean
  MappedInterceptor jwtHandler() {
    return new MappedInterceptor(new String[] {"/api/v1/**"}, new JWTAuthorizationFilter());
  }
}
