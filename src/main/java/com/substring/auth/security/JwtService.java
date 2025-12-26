package com.substring.auth.security;


import com.substring.auth.entities.Role;
import com.substring.auth.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.jsonwebtoken.Jwts.parser;

@Service
@Getter
@Setter
public class JwtService {
    private final SecretKey key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;
    public JwtService(
            @Value("${spring.security.jwt.secret}") String secret,
            @Value("${spring.security.jwt.access-ttl-seconds}") long accessTtlSeconds,
            @Value("${spring.security.jwt.refresh-ttl-seconds}") long refreshTtlSeconds,
            @Value("${spring.security.jwt.issuer}") String issuer) {
        if (secret == null || secret.length() < 64) {
            throw new IllegalArgumentException("Invalid secret");
        }

        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issuer = issuer;
    }

    //generate token:
    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        List<String> roles = user.getRoles() == null ? List.of() :
                user.getRoles().stream().map(Role::getName).toList();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .claim("email", user.getEmail())
                .claim("roles", roles)
                .claim("typ", "access")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // generate refreshotken.
    public String generateRefreshToken(User user, String jti) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setId(jti)
                .setSubject(user.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .claim("typ", "refresh")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //parse the token

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)             // set the secret key
                .build()
                .parseClaimsJws(token);         // parse the signed JWT
    }

    public boolean isAccessToken(String token) {
        Claims c = parse(token).getBody();
        return "access".equals(c.get("typ"));
    }


    public boolean isRefreshToken(String token) {
        Claims c = parse(token).getBody();
        return "refresh".equals(c.get("typ"));
    }

    public UUID getUserId(String token) {
        Claims c = parse(token).getBody();
        return UUID.fromString(c.getSubject());
    }

    public String getJti(String token) {
        return parse(token).getBody().getId();
    }

    public List<String> getRoles(String token) {
        Claims c = parse(token).getBody();
        return (List<String>) c.get("roles");
    }

    public String getEmail(String token) {
        Claims c = parse(token).getBody();
        return (String) c.get("email");
    }
}
