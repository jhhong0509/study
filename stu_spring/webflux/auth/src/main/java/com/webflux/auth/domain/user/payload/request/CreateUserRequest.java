package com.webflux.auth.domain.user.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String email;

    private String password;

}
