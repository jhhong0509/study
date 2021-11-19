package com.kotlin.test.kotlintest.global.exception

import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class MethodArgumentException : GlobalException(ErrorCode.METHOD_ARGUMENT) {
    companion object {
        @JvmField
        val EXCEPTION = MethodArgumentException()
    }
}