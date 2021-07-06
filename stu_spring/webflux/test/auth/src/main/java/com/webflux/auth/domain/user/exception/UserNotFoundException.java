package com.webflux.auth.domain.user.exception;

import com.webflux.auth.global.error.exception.ErrorCode;
import com.webflux.auth.global.error.exception.GlobalException;

public class UserNotFoundException extends GlobalException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
