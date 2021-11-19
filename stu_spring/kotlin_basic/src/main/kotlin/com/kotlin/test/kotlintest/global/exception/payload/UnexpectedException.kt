package com.kotlin.test.kotlintest.global.exception.payload

import com.kotlin.test.kotlintest.global.exception.GlobalException
import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class UnexpectedException : GlobalException(ErrorCode.UNEXPECTED_EXCEPTION) {
    companion object {
        @JvmField
        val EXCEPTION = UnexpectedException()
    }
}