package com.webflux.auth.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("User Not Found Exception", 404, "User Error"),
    USER_ALREADY_EXIST("User Id Already Exist Exception", 409, "User Error"),
    INVALID_TOKEN("Invalid Token Exception", 401, "Token Error");

    private final String message;
    private final int status;
    private final String error;
}
