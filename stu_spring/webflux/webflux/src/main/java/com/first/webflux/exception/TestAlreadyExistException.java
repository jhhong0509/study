package com.first.webflux.exception;

import com.first.webflux.error.ErrorCode;
import com.first.webflux.error.GlobalException;

public class TestAlreadyExistException extends GlobalException {
    public TestAlreadyExistException() {
        super(ErrorCode.TEST_ALREADY_EXIST);
    }
}
