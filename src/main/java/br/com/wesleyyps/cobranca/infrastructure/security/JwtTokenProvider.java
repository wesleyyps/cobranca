package br.com.wesleyyps.cobranca.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.wesleyyps.cobranca.domain.exceptions.CobrancaException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

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

    public Boolean validateToken(String token) throws CobrancaException {
        try {
            Jwts.parser()
                .verifyWith(this.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return true;
        } catch (SignatureException ex) {
            throw new CobrancaException(
                HttpStatus.BAD_REQUEST,
                "Invalid jwt token signature"
            );
        } catch (MalformedJwtException ex) {
            throw new CobrancaException(
                HttpStatus.BAD_REQUEST,
                "Invalid jwt token"
            );
        } catch (ExpiredJwtException ex) {
            throw new CobrancaException(
                HttpStatus.BAD_REQUEST,
                "Expired jwt token"
            );
        } catch (UnsupportedClassVersionError ex) {
            throw new CobrancaException(
                HttpStatus.BAD_REQUEST,
                "Unsuported jwt token"
            );
        } catch (IllegalArgumentException ex) {
            throw new CobrancaException(
                HttpStatus.BAD_REQUEST,
                "Empty jwt claim"
            );
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(this.getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return claims.getSubject();
    }
}
