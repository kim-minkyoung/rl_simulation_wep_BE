package com.example.rl_simulation_wep.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import io.github.cdimascio.dotenv.Dotenv;


@Component
public class JwtTokenUtil {

    private static final SecretKey SECRET_KEY;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    static {
        Dotenv dotenv = Dotenv.load(); // .env 파일 로드
        String secretKeyValue = dotenv.get("SECRET_KEY"); // 비밀 키 가져오기
        SECRET_KEY = Keys.hmacShaKeyFor(secretKeyValue.getBytes()); // SecretKey 생성
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public static Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return Long.valueOf(claims.get("userId").toString());
    }
}
