package org.daemio.merch.config;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class WebSecurityConfig {

  /** */
  private static final String ROLE_PREFIX = "ROLE_";

  /** */
  private static final String ROLES_CLAIM_NAME = "roles";

  @Value("${app.security.jwt.secret-key}")
  private transient String secretKey;

  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http, JwtAuthenticationConverter authenticationConverter) throws Exception {
    log.info("Initializing security chain");

    http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable);

    http.headers(headers -> headers.cacheControl().disable());
    http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // security matcher way of doing it. if we go this route then the space controller
    // becomes its own security matcher bean in a separate method
    // http.securityMatcher("/merch/**")
    //     .authorizeHttpRequests(
    //         authz -> {
    //           authz
    //               .requestMatchers(HttpMethod.GET)
    //               .permitAll()
    //               .anyRequest()
    //               .hasRole(RoleConfig.VENDOR.getAuthority());
    //         });

    http.authorizeHttpRequests(
        authz -> {
          authz
              .requestMatchers(HttpMethod.GET, "/api/v1/merch/**")
              .hasRole(RoleConfig.FAN.getAuthority())
              .requestMatchers("/api/v1/merch/**")
              .hasRole(RoleConfig.VENDOR.getAuthority())
              .requestMatchers(HttpMethod.GET, "/actuator/health/**", "/actuator/info")
              .permitAll();
        });

    http.oauth2ResourceServer(
        server -> server.jwt().jwtAuthenticationConverter(authenticationConverter));

    return http.build();
  }

  @Bean
  JwtDecoder decoder() {
    return NimbusJwtDecoder.withSecretKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build();
  }

  @Bean
  JwtAuthenticationConverter authenticationConverter() {
    var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
    authoritiesConverter.setAuthorityPrefix(ROLE_PREFIX);
    authoritiesConverter.setAuthoritiesClaimName(ROLES_CLAIM_NAME);
    var converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
    return converter;
  }
}
