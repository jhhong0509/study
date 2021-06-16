package com.webflux.auth.global.security.auth;

import com.webflux.auth.global.security.jwt.TokenType;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenInformation {

    private String token;

    private TokenType tokenType;

    private Claims claims;

}
