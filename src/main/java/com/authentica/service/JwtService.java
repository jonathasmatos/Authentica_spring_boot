package com.authentica.service;

import com.authentica.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Especialista em emissão de Tokens JWT.
 */
@Service
public class JwtService {

    @Value("${api.security.token.secret:minha-chave-secreta-que-deve-ter-pelo-menos-32-caracteres}")
    private String secret;

    public String generateToken(User user) {
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .toList();

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 7200000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
