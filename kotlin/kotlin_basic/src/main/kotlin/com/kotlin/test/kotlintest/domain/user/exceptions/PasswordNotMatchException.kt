package com.kotlin.test.kotlintest.domain.user.exceptions

import com.kotlin.test.kotlintest.global.exception.GlobalException
import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class PasswordNotMatchException: GlobalException(ErrorCode.PASSWORD_NOT_MATCH) {
    companion object {
        @JvmField
        val EXCEPTION = PasswordNotMatchException()
    }
}