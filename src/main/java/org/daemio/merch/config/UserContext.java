package org.daemio.merch.config;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContext {
  private UUID userId;

  public boolean isPublicUser() {
    return userId == null;
  }
}
