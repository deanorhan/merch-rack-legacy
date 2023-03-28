package org.daemio.merch.util;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {
    
    public static Instant truncateToSeconds(Instant instant) {
        return instant.truncatedTo(SECONDS);
    }
}
