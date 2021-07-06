package com.webflux.auth.domain.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BlogResponse {

    private final String id;

    private final String title;

    private final String userEmail;

}
