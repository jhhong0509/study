package com.webflux.auth.domain.auth.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String email;

    private String password;

}
