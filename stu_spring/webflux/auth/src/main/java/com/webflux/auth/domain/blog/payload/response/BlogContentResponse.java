package com.webflux.auth.domain.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogContentResponse {

    private String title;

    private String content;

    private String userEmail;

}
