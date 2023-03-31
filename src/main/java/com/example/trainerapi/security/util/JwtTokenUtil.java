package com.example.trainerapi.security.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.Objects;

public class JwtTokenUtil {
    private static final long EXPIRATION_TIME = 10 * 24 * 60 * 60 * 1000; // 10 days

    private static final Dotenv dotenv = Dotenv.configure().load();
    private static final String secret = Objects.requireNonNull(dotenv.get("JWT_SECRET"));

    public static String generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, convertBase64(secret))
                .compact();
    }

    private static String convertBase64(String str) {
        Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(str.getBytes()));
    }

    public static boolean validate(String token, UserDetails userDetails) {
        String username = parseSubject(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(convertBase64(secret))
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }

    public static String getUsername(String token) {
        return parseSubject(token);
    }

    private static String parseSubject(String token) {
        return Jwts.parser()
                .setSigningKey(convertBase64(secret))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
