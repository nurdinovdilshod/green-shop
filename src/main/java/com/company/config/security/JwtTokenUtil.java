package com.company.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {
    public static final String SECRET_KEY = "baddilshod237bb22ff535d452a8016c6875914be991e63c1e787ca207727b2e6be9c54bd4";

    public String generateJwtToken(@NonNull String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuer("https://green-shop")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 300 * 60 * 1000))
                .signWith(signKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Claims getClaims(@NonNull String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key signKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String getSubject(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
}
