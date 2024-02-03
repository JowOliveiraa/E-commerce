package com.example.ecommerce.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.ecommerce.models.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${secret.key}")
    private String secretKey;

    @Value("${token.expiration.time}")
    private Long expirationTime;

    public String generateToken(User user) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer("ecommerce")
                    .withSubject(user.getCpf())
                    .withExpiresAt(expiresAt())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {

            throw new RuntimeException("Erro na criação do token: ", exception);
        }
    }

    public String validateToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer("ecommerce")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {

            return "";
        }
    }

    private Instant expiresAt() {

        return LocalDateTime.now().plusHours(expirationTime).toInstant(ZoneOffset.of("-03:00"));
    }
}
