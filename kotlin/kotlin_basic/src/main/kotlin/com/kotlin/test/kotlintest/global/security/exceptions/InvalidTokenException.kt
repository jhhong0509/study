package com.kotlin.test.kotlintest.global.security.exceptions

import com.kotlin.test.kotlintest.global.exception.GlobalException
import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class InvalidTokenException : GlobalException(ErrorCode.INVALID_TOKEN) {
    companion object {
        @JvmField
        val EXCEPTION = InvalidTokenException()
    }
}