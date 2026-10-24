package com.employee.api.gatway.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import io.jsonwebtoken.security.Keys;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key getSigningKey() {
      //  return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        byte[] keyBytes = Decoders.BASE64.decode(secret);
       return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {

            log.info(" Error : {}",ex);
            return false;
        }
    }
}
