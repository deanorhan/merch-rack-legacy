package org.daemio.merch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // (
// auditorAwareRef = "auditorAware",
// dateTimeProviderRef = "auditingDateTimeProvider")
public class PersistenceConfig {

  //   @Bean
  //   AuditorAware<String> auditorAware() {
  //     return () -> Optional.of("merch_service");
  //   }

  //   @Bean(name = "auditingDateTimeProvider")
  //   public DateTimeProvider dateTimeProvider() {
  //     return () -> Optional.of(Instant.now());
  //   }
}
