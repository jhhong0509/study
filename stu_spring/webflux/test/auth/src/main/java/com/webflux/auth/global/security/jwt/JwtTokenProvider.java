package com.webflux.auth.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${auth.secret}")
    private String secret;

    public Mono<String> generateToken(String email, TokenType tokenType) {
        return Mono.just(
                Jwts.builder()
                        .setSubject(email)
                        .setIssuedAt(new Date())
                        .signWith(SignatureAlgorithm.HS256, getSecret())
                        .setExpiration(new Date(System.currentTimeMillis() + (tokenType.getExp() * 1000)))
                        .claim("type", tokenType.getType())
                        .compact()
        );
    }

    public Mono<Claims> parseToken(String token) {
        return Mono.just(
                Jwts.parser().setSigningKey(getSecret())
                        .parseClaimsJws(token).getBody()
        );
    }

    private byte[] getSecret() {
        return Base64.getEncoder().encode(secret.getBytes());
    }

}
