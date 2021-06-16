package com.webflux.auth.domain.blog.service;

import com.webflux.auth.domain.blog.payload.request.CreateBlogRequest;
import com.webflux.auth.domain.user.entity.User;
import reactor.core.publisher.Mono;

public interface BlogService {
    Mono<Void> createBlog(CreateBlogRequest request, String email);
}
