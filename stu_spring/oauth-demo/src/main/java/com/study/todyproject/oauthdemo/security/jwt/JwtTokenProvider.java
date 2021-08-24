package com.study.todyproject.oauthdemo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final AuthDetailsService authDetailsService;
    private static final String SECRET = "testJwt";

    public String createToken(String email, String type) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 7200000))
                .setSubject(email)
                .setHeaderParam("kin", type)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(SECRET.getBytes()))
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parser().setSigningKey(Base64.getEncoder().encode(SECRET.getBytes())).parseClaimsJws(token.split(" ")[1]).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Authentication getAuthentication(String email) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
