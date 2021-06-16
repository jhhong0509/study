package com.webflux.auth.domain.blog.service;

import com.webflux.auth.domain.blog.entity.Blog;
import com.webflux.auth.domain.blog.entity.BlogRepository;
import com.webflux.auth.domain.blog.payload.request.CreateBlogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public Mono<Void> createBlog(CreateBlogRequest request, String email) {
        return Mono.just(
                Blog.builder()
                        .content(request.getContent())
                        .title(request.getTitle())
                        .userEmail(email)
                        .build())
                .flatMap(blogRepository::save)
                .then();
    }
}
