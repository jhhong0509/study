package com.kotlin.test.kotlintest.global.exception

import com.kotlin.test.kotlintest.global.exception.enums.ErrorCode
import com.kotlin.test.kotlintest.global.exception.payload.ErrorResponse
import com.kotlin.test.kotlintest.global.exception.payload.UnexpectedException
import com.kotlin.test.kotlintest.global.security.exceptions.InvalidTokenException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.NestedServletException
import java.io.InvalidClassException
import java.security.InvalidKeyException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionHandlerFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        return try {
            filterChain.doFilter(request, response)
        } catch (exception: NestedServletException) {
            writeErrorCodes(exception, response)
        } catch (exception: Exception) {
            writeErrorCodes(UnexpectedException.EXCEPTION, response)
        }
    }

    private fun writeErrorCodes(e: NestedServletException, response: HttpServletResponse) {
        logger.error("Error Occurred", e);

        val exception = e.cause

        if (exception is GlobalException) {
            writeErrorCodes(exception, response)
        } else {
            writeErrorCodes(UnexpectedException.EXCEPTION, response)
        }

    }

    private fun writeErrorCodes(e: GlobalException, response: HttpServletResponse) {
        logger.error("Error Occurred", e);

        val errorCode = e.code
        val errorResponse = ErrorResponse(errorCode = errorCode)

        response.contentType = "application/json"
        response.status = errorCode.status
        response.writer.write(errorResponse.toString())
    }
}