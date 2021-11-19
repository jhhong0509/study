package com.kotlin.test.kotlintest.global.exception

import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class NoHandlerException : GlobalException(ErrorCode.NO_HANDLER) {
    companion object {
        @JvmField
        val EXCEPTION = NoHandlerException()
    }
}