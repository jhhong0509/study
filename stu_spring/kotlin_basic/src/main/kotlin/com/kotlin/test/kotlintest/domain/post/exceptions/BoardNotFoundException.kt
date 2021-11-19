package com.kotlin.test.kotlintest.domain.post.exceptions

import com.kotlin.test.kotlintest.global.exception.GlobalException
import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class BoardNotFoundException : GlobalException(ErrorCode.POST_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = BoardNotFoundException()
    }
}