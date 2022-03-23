package com.kotlin.test.kotlintest.domain.post.exceptions

import com.kotlin.test.kotlintest.global.exception.GlobalException
import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class BoardNotAccessibleException : GlobalException(ErrorCode.POST_NOT_ACCESSIBLE) {
    companion object {
        @JvmField
        val EXCEPTION = BoardNotAccessibleException()
    }
}