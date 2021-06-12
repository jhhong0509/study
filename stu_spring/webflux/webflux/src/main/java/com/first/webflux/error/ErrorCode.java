package com.first.webflux.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    TEST_NOT_FOUND("Test Not Found", 404),
    TEST_ALREADY_EXIST("Test Already Exist", 409),
    UNKNOWN_ERROR("Unknown Error", 500);
    private final String error;
    private final int status;
}
