package com.template.project.infra.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.project.infra.entities.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;

@Service
public class Token {

    private static final Logger log = LoggerFactory.getLogger(Token.class);

    private static final String ISSUE = "template-api";

    private static final String ALGORITHM = "SHA-256";

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public boolean isValidToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) return false;
            byte[] keyBytes = hashSecretKey(this.secret);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String generateTokenJwt(Authentication authentication) {
        try {
            var user = (User) authentication.getPrincipal();
            var today = new Date();
            var expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
            String json = new ObjectMapper().writer().writeValueAsString(user);
            byte[] keyBytes = hashSecretKey(this.secret);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);
            // Convert key to a base64 string to store/reuse
            return Jwts.builder()
                    .setIssuer(ISSUE)
                    .setSubject(json)
                    .setIssuedAt(today)
                    .setExpiration(expirationDate)
                    .signWith(key)
                    .compact();
        } catch (Exception ex) {
            log.error("Error: {}", ex.getMessage());
            throw new RuntimeException("Error to try generate token");
        }
    }

    public String getUserUuid(String token) {
        try {
            byte[] keyBytes = hashSecretKey(this.secret);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            var body = jws.getBody().getSubject();
            var user = new ObjectMapper().readValue(body, UserEntity.class);
            return user.getId();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    private static byte[] hashSecretKey(String secret) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            return digest.digest(secret.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Error generating SHA-256 hash", e);
        }
    }
}
