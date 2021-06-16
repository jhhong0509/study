package com.webflux.auth.domain.blog.entity;

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

    private final String title;

    private final String content;

    private final String userEmail;

}
