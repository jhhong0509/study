package com.webflux.auth.global.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {
    ACCESS("access", Expiration.accessExp),
    REFRESH("refresh", Expiration.refreshExp);

    private final String type;
    private final long exp;

}
