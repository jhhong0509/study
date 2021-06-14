package com.first.webflux.entity;

import com.first.webflux.dto.TestUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@Builder
public class Test {

    @Id
    private final String id;

    private String title;

    private String content;

    public void update(TestUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

}
