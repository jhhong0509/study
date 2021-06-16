package com.webflux.auth.domain.user.exception;

import com.webflux.auth.global.error.exception.ErrorCode;
import com.webflux.auth.global.error.exception.GlobalException;

public class UserAlreadyExistException extends GlobalException {
    public UserAlreadyExistException() {
        super(ErrorCode.USER_ALREADY_EXIST);
    }
}
