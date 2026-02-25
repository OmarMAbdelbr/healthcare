package com.kfh.healthcare.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Autowired
    CacheManager cacheManager;

    private final Key key;

    @Value("${app.jwt.expiration-ms}")
    private Integer tokenExpirationMins;

    public JwtUtils(@Value("${app.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + (tokenExpirationMins*60*1000)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public void blacklistToken(String token) {
        cacheManager.getCache("blacklistedTokens").put(token, true);
    }

    public boolean isTokenBlacklisted(String token) {
        return cacheManager.getCache("blacklistedTokens").get(token) != null;
    }

    public boolean validateToken(String authToken) {
        try {
            if (isTokenBlacklisted(authToken)) return false;
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
