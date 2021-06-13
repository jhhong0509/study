package com.first.webflux.entity;

import com.first.webflux.dto.TestRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@Builder
public class Test {

    @Id
    private String id;

    private String title;

    private String content;

    public void update(TestRequest request) {
        this.id = request.getId();
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}
