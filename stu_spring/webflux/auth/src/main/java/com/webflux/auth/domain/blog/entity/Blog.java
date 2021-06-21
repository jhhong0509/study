package com.webflux.auth.domain.blog.entity;

import com.webflux.auth.domain.blog.payload.request.CreateBlogRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document
public class Blog {

    @Id
    private final String id;

    private String title;

    private String content;

    private final String userEmail;

    public void updateBlog(CreateBlogRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}
