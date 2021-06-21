package com.webflux.auth.domain.blog.router;

import com.webflux.auth.domain.blog.handler.BlogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Component
public class BlogRouter {

    private final BlogHandler blogHandler;

    @Bean
    public RouterFunction<ServerResponse> blogRoute() {
        return route().path("/blog", builder ->
                builder.nest(accept(MediaType.APPLICATION_JSON), routes -> routes
                        .POST("", blogHandler::createBlog))
                        .GET("/list", blogHandler::getBlogList)
                        .GET("{blogId}", blogHandler::getBlogResponse)
                        .DELETE("/{blogId}", blogHandler::deleteBlog)
                        .PATCH("/{blogId}", blogHandler::updateBlog)
        )
                .build();
    }

}
