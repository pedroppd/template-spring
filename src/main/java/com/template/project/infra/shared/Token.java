package com.template.project.infra.shared;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.project.infra.entities.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class Token {

    private static final Logger log = LoggerFactory.getLogger(Token.class);

    private static final String ISSUE = "template-api";

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    public String generateTokenJwt(Authentication authentication) throws JsonProcessingException {
        var user = (UserEntity) authentication.getPrincipal();
        var today = new Date();
        var expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        String json = new ObjectMapper().writer().writeValueAsString(user);
        return Jwts.builder()
                .setIssuer(ISSUE)
                .setSubject(json)
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUserUuid(String token) {
        try {
            var body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
            var subject = body.getSubject();
            var user = new ObjectMapper().readValue(subject, UserEntity.class);
            return user.getId();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }
}
