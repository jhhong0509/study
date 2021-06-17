package com.webflux.auth.domain.blog.exception;

import com.webflux.auth.global.error.exception.ErrorCode;
import com.webflux.auth.global.error.exception.GlobalException;

public class QueryParameterNotFoundException extends GlobalException {
    public QueryParameterNotFoundException() {
        super(ErrorCode.QUERY_PARAMETER_NOTFOUND);
    }
}
