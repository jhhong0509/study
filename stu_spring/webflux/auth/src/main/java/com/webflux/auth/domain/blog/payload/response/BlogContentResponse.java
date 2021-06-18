package com.webflux.auth.domain.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogContentResponse {

    private String title;

    private String content;

    private String userEmail;

}
