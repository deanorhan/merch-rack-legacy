package org.daemio.merch.config;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    @Autowired
    private RoleConfig roleConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Initializing security chain");

        http.cors(t -> t.disable())
            .csrf(t -> t.disable());

        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.POST, "/merch").hasRole(roleConfig.getVendor())
                .anyRequest().permitAll();
        });

        http.httpBasic(withDefaults());

        

        return http.build();
    }

    @Bean
    @SuppressWarnings("deprecation")
    public InMemoryUserDetailsManager userDetails() {
        return new InMemoryUserDetailsManager(User.withDefaultPasswordEncoder()
            .username("test")
            .password("pass")
            .roles(roleConfig.getVendor())
            .build());
    }
}
