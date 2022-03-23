package com.kotlin.test.kotlintest.global.exception

import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode

open class GlobalException(val code: ErrorCode) : RuntimeException()