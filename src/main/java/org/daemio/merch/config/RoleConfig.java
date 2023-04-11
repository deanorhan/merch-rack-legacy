package org.daemio.merch.config;

import org.springframework.security.core.GrantedAuthority;

public enum RoleConfig implements GrantedAuthority {
  ADMIN,
  FAN,
  STAN,
  VENDOR;

  @Override
  public String getAuthority() {
    return name();
  }
}
