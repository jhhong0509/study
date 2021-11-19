package com.kotlin.test.kotlintest.global.security.exceptions

import com.kotlin.test.kotlintest.global.exception.GlobalException
import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class TokenExpiredException : GlobalException(ErrorCode.TOKEN_EXPIRED) {
    companion object {
        @JvmField
        val EXCEPTION = TokenExpiredException()
    }
}