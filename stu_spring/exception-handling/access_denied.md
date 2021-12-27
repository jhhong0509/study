# AccessDenied

우리가 만들어서 쓰는 Filter는 Dispatcher Servlet 앞에서 요청을 걸러주는 역할을 한다.

하지만 ControllerAdvice는 Controller에서 발생한 요청만 걸러주는 역할을 한다.

따라서 **Filter에서 발생한 예외는 ControllerAdvice에서 Handle할 수 없다.**

## ExceptionFilter

이를 해결하기 위해 **ExceptionFilter를 만들어 다른 Filter들 이전에 Exception을 Catch해주는 방법**을 사용했다.

### ExceptionFilter

``` kotlin
class ExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (exception: Exception) {
            when(exception) {
                is GlobalException -> writeErrorCode(exception, response)
                else -> writeErrorCode(InternalServerError.EXCEPTION, response)
            }
        }
    }

    private fun writeErrorCode(exception: GlobalException, response: HttpServletResponse) {
        val errorResponse = BaseResponse.of(exception)

        response.characterEncoding = "UTF-8"
        response.status = errorResponse.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}
```

### FilterConfig

``` kotlin
class FilterConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(builder: HttpSecurity) {
        val tokenFilter = TokenFilter(jwtTokenProvider)
        val exceptionFilter = ExceptionFilter(objectMapper)
        builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionFilter, TokenFilter::class.java)
    }
}
```

위와 같이 ExceptionFilter를 다른 Filter 이전에 설정해서 예외를 catch해 주도록 설정했다.

