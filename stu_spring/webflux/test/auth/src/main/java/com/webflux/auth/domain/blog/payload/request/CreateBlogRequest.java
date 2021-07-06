package com.webflux.auth.domain.blog.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBlogRequest {

    private String title;

    private String content;

}
