package org.daemio.merch.util;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.time.temporal.ChronoUnit.SECONDS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {

  public static Instant truncateToSeconds(Instant instant) {
    return instant.truncatedTo(SECONDS);
  }
}
