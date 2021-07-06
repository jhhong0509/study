package com.webflux.auth.global.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Expiration {
    public static long accessExp;

    public static long refreshExp;

    @Value("${auth.exp.access}")
    public void setAccessExp(long accessExp) {
        Expiration.accessExp = accessExp;
    }

    @Value("${auth.exp.refresh}")
    public void setRefreshExp(long refreshExp) {
        Expiration.refreshExp = refreshExp;
    }

}
