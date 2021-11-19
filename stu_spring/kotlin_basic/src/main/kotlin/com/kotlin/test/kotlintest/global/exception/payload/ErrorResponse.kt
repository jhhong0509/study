package com.kotlin.test.kotlintest.global.exception.payload

import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

class ErrorResponse(private val errorCode: ErrorCode) {
    override fun toString() =
        """
            {
                "status": ${this.errorCode.status}
                "cause": "${this.errorCode.cause}"
            }
        """.trimIndent()
}