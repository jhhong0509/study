package com.webflux.auth.domain.blog.service;

import com.webflux.auth.domain.blog.entity.Blog;
import com.webflux.auth.domain.blog.entity.BlogRepository;
import com.webflux.auth.domain.blog.exception.BlogNotFoundException;
import com.webflux.auth.domain.blog.payload.request.CreateBlogRequest;
import com.webflux.auth.domain.blog.payload.response.BlogContentResponse;
import com.webflux.auth.domain.blog.payload.response.BlogListResponse;
import com.webflux.auth.domain.blog.payload.response.BlogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    @Override
    public Mono<BlogListResponse> getBlogList(Pageable pageable) {
        Flux<BlogResponse> blogs = blogRepository.findAllBy(pageable)
                .flatMap(this::buildBlogResponse);

        return Mono.zip(blogs.collectList(), blogs.count())
                .flatMap(objects -> Mono.just(new BlogListResponse(objects.getT2(), objects.getT1())));
    }

    @Override
    public Mono<BlogContentResponse> getBlog(String blogId) {
        return blogRepository.findById(blogId)
                .flatMap(this::buildResponse)
                .switchIfEmpty(Mono.error(BlogNotFoundException::new));
    }

    @Override
    public Mono<Void> deleteBlog(String blogId, String userEmail) {
        return blogRepository.findById(blogId)
                .filter(blog -> blog.getUserEmail().equals(userEmail))
                .flatMap(blogRepository::delete)
                .switchIfEmpty(Mono.error(BlogNotFoundException::new));
    }

    @Override
    public Mono<Void> updateBlog(CreateBlogRequest request, String blogId) {
        return null;
    }

    private Mono<BlogContentResponse> buildResponse(Blog blog) {
        return Mono.just(
                BlogContentResponse.builder()
                        .content(blog.getContent())
                        .title(blog.getTitle())
                        .userEmail(blog.getUserEmail())
                        .build()
        );
    }

    private Mono<BlogResponse> buildBlogResponse(Blog blog) {
        return Mono.just(
                BlogResponse.builder()
                        .id(blog.getId())
                        .title(blog.getTitle())
                        .userEmail(blog.getUserEmail())
                        .build()
        );
    }

}
