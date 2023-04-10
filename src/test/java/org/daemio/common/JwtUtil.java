package org.daemio.common;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtil {

  public static String getValidFan() {
    var claims = new HashMap<String, String>();
    claims.put("roles", "FAN");

    return createJwt(claims);
  }

  public static String getValidStan() {
    var claims = new HashMap<String, String>();
    claims.put("roles", "STAN");

    return createJwt(claims);
  }

  public static String getValidVendor() {
    var claims = new HashMap<String, String>();
    claims.put("roles", "VENDOR");

    return createJwt(claims);
  }

  private static String createJwt(HashMap<String, ?> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plusSeconds(60)))
        .setId(UUID.randomUUID().toString())
        .signWith(
            Keys.hmacShaKeyFor(System.getenv("SECRET_KEY").getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }
}
