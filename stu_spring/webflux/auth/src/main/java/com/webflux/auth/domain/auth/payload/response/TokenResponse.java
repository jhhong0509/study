package com.webflux.auth.domain.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

}
