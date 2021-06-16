package com.webflux.auth.domain.blog.handler;

import com.webflux.auth.domain.blog.payload.request.CreateBlogRequest;
import com.webflux.auth.domain.blog.service.BlogService;
import com.webflux.auth.global.security.auth.facade.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class BlogHandler {

    private final BlogService blogService;
    private final AuthenticationFacade authenticationFacade;

    public Mono<ServerResponse> createBlog(ServerRequest request) {
        Mono<Void> createBlogRequest = request.bodyToMono(CreateBlogRequest.class)
                .flatMap(req -> getCreateBlogResult(req, authenticationFacade.getUserEmail(request)));

        return ServerResponse.created(URI.create("/blog"))
                .body(createBlogRequest, Void.class);
    }

    private Mono<Void> getCreateBlogResult(CreateBlogRequest request, Mono<String> email) {
        return email
                .flatMap(userEmail -> blogService.createBlog(request, userEmail));
    }
}
