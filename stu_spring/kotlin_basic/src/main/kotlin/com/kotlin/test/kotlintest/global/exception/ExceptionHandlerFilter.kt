package com.kotlin.test.kotlintest.global.exception

import com.kotlin.test.kotlintest.global.exception.payload.ErrorResponse
import com.kotlin.test.kotlintest.global.exception.payload.UnexpectedException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionHandlerFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        return try {
            filterChain.doFilter(request, response)
        } catch (exception: GlobalException) {
            writeErrorCodes(exception, response)
        } catch (exception: Exception) {
            writeErrorCodes(UnexpectedException.EXCEPTION, response)
        }
    }

    private fun writeErrorCodes(e: GlobalException, response: HttpServletResponse) {
        val errorCode = e.code
        val errorResponse = ErrorResponse(errorCode = errorCode)

        response.contentType = "application/json"
        response.status = errorCode.status
        response.writer.write(errorResponse.toString())
    }
}