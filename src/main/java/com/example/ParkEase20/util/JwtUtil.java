package com.example.ParkEase20.util;

import com.example.ParkEase20.config.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {


    private final long EXPIRATION_DAY = 30;
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secretKeyBase64) {
        byte[] decodedKey = Base64.getUrlDecoder().decode(secretKeyBase64);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }
    private Date getExpirationDate() {
        return Date.from(Instant.now().plus(EXPIRATION_DAY, ChronoUnit.DAYS));
    }


    public String generateToken(CustomUserDetails customUserDetails) {
        String role=customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).findFirst().orElse("ROlE_USER");
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(customUserDetails.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

    }
    private Claims parseToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        return !isTokenExpired(token);
    }

    public Long extractUserIdFromToken(String token) {
        return Long.valueOf(parseToken(token).getSubject());
    }
    public boolean isTokenExpired(String token){
       Date expiry= parseToken(token).getExpiration();
       return expiry.before(new Date());
    }

}
