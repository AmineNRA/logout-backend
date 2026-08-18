package com.logout.backend.configuration;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.logout.backend.model.Profil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.expiration-time}")
    private long expirationTime;

    // --- Génération du Token ---
    public String generateToken(Profil profil) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(profil.getPseudo())
                .claim("profilId", profil.getId())
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationTime))
                .signWith(getSignKey())
                .compact();
    }

    // --- Validation du Token ---
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.getSubject());
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, claims -> claims.getExpiration());
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    // --- Extraction générique des Claims ---
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Integer extractProfilId(String token) {
        return extractClaim(token, claims -> claims.get("profilId", Integer.class));
    }
}
