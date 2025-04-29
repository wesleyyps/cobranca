package br.com.wesleyyps.cobranca.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider 
{
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMilliseconds;

    public String generateToken(
        Authentication authentication,
        Date expireDate,
        ClaimsBuilder claims
    ) {
        String username = authentication.getName();

        claims.subject(username);
        claims.issuedAt(new Date());
        claims.expiration(expireDate);

        JwtBuilder builder = Jwts.builder();
        builder.claims().empty().add(claims.build()).and();

        return builder
            .signWith(this.getSecretKey())
            .compact();
    }

    public String generateToken(Authentication authentication) {
        Date currentDate = new Date();
        Date expireDate = new Date(
            currentDate.getTime() + this.jwtExpirationInMilliseconds
        );
        return this.generateToken(
            authentication,
            expireDate,
            Jwts.claims()
        );
    }

    private SecretKey getSecretKey() {
        return Keys
            .hmacShaKeyFor(
                jwtSecret
                    .getBytes(StandardCharsets.UTF_8)
            );
    }
}
