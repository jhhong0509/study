package com.webflux.auth.domain.auth.exception;

import com.webflux.auth.global.error.exception.ErrorCode;
import com.webflux.auth.global.error.exception.GlobalException;

public class InvalidTokenException extends GlobalException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
