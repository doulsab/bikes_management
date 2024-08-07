package com.dd.bikes.jwt;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTTokenConfig {
	
	private final JwtKeyResponse jwtResponse;

    public String createToken(String username) {
        return JWT.create()
            .withIssuer("ddtechnosoftindia")
            .withSubject(username)
            .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
            .sign(Algorithm.HMAC256(jwtResponse.getSecreteKey()));
    }
}
