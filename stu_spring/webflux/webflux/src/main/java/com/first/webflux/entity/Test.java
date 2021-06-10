package com.first.webflux.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Test {

    @Id
    private String id;

    private String title;

    private String content;

}
