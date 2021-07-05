package com.webflux.auth.domain.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BlogListResponse {

    private final long totalElements;

    private final List<BlogResponse> blogResponses;

}
