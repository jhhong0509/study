package com.kotlin.test.kotlintest.domain.user.exceptions

import com.kotlin.test.kotlintest.global.exception.GlobalException
import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class UserNotFoundException: GlobalException(ErrorCode.USER_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = UserNotFoundException()
    }
}