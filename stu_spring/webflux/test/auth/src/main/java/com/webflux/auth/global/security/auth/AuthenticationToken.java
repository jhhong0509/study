package com.webflux.auth.global.security.auth;

import com.webflux.auth.global.security.jwt.TokenType;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private final TokenType tokenType;
    private final String email;
    private final String decodedToken;

    public AuthenticationToken(TokenType tokenType, String email, String decodedToken) {
        super(null);
        this.tokenType = tokenType;
        this.email = email;
        this.decodedToken = decodedToken;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return decodedToken;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }
}
