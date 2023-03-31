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

/**
 * Utility class for generating and validating JWT tokens.
 */
public class JwtTokenUtil {
    private static final long EXPIRATION_TIME = 10 * 24 * 60 * 60 * 1000; // 10 days

    private static final Dotenv dotenv = Dotenv.configure().load();
    private static final String secret = Objects.requireNonNull(dotenv.get("JWT_SECRET"));

    /**
     * Generates a JWT token for the given username.
     * @param username the username to generate the token for
     * @return the generated token
     */
    public static String generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, convertBase64(secret))
                .compact();
    }

    /**
     * Converts the given string to a base64 encoded string.
     * @param str the string to convert
     * @return the converted string
     */
    private static String convertBase64(String str) {
        Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(str.getBytes()));
    }

    /**
     * Validates the given token for the given user details.
     * @param token the token to validate
     * @param userDetails the user details to validate the token for
     * @return true if the token is valid, false otherwise
     */
    public static boolean validate(String token, UserDetails userDetails) {
        String username = parseSubject(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if the given token is expired.
     * @param token the token to check
     * @return true if the token is expired, false otherwise
     */
    private static boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(convertBase64(secret))
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }

    /**
     * Parses the username from the given token.
     * @param token the token to parse the username from
     * @return the username
     */
    public static String getUsername(String token) {
        return parseSubject(token);
    }

    /**
     * Parses the subject from the given token.
     * @param token the token to parse the subject from
     * @return the subject
     */
    private static String parseSubject(String token) {
        return Jwts.parser()
                .setSigningKey(convertBase64(secret))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
