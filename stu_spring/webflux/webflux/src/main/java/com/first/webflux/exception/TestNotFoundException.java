package com.first.webflux.exception;

import com.first.webflux.error.ErrorCode;
import com.first.webflux.error.GlobalException;

public class TestNotFoundException extends GlobalException {
    public TestNotFoundException() {
        super(ErrorCode.TEST_NOT_FOUND);
    }
}
