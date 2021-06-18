package com.webflux.auth.domain.blog.exception;

import com.webflux.auth.global.error.exception.ErrorCode;
import com.webflux.auth.global.error.exception.GlobalException;

public class BlogNotFoundException extends GlobalException {
    public BlogNotFoundException() {
        super(ErrorCode.BLOG_NOT_FOUND);
    }
}
