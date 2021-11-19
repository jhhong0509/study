package com.kotlin.test.kotlintest.domain.user.exceptions

import com.kotlin.test.kotlintest.global.exception.GlobalException
import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class UserAlreadyExistException: GlobalException(ErrorCode.USER_ALREADY_EXIST) {
    companion object {
        @JvmField
        val EXCEPTION = UserAlreadyExistException()
    }
}