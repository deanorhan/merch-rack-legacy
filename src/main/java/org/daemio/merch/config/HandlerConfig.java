package org.daemio.merch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.daemio.merch.filter.LoggedInMethodHandler;

@Configuration
@RequiredArgsConstructor
public class HandlerConfig implements WebMvcConfigurer {

  private final LoggedInMethodHandler loggedInMethodHandler;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loggedInMethodHandler).addPathPatterns("/api/v1/**");
  }
}
