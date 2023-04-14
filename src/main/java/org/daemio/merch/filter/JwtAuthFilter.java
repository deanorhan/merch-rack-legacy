package org.daemio.merch.filter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import org.daemio.merch.config.RoleConfig;
import org.daemio.merch.config.UserContext;

@Component
@Profile("!unit-test")
@Slf4j
public class JwtAuthFilter implements Filter {

  @Autowired private transient UserContext userContext;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    var sc = SecurityContextHolder.getContext();

    if (sc.getAuthentication() instanceof JwtAuthenticationToken) {
      var jwtAuth = (JwtAuthenticationToken) sc.getAuthentication();

      var userId = (String) jwtAuth.getTokenAttributes().get("userId");
      log.info("User id coming in is {}", userId == null ? "PUBLIC" : userId);

      var roles = jwtAuth.getTokenAttributes().get("roles");
      if (userId == null
          && (roles instanceof List || !RoleConfig.FAN.getAuthority().equals((String) roles))) {
        throw new AuthorizationServiceException("too many rolles for a public user");
      }

      userContext.setUserId(convertUserId(userId));

    } else {
      throw new AuthorizationServiceException("not a jwt");
    }

    chain.doFilter(request, response);

    RequestContextHolder.resetRequestAttributes();
  }

  private UUID convertUserId(String userId) {
    if (userId == null) {
      return null;
    }

    return UUID.fromString(userId);
  }
}
