package com.webflux.auth.domain.user.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
public class User {

    @Id
    private String _id;

    private String email;

    private String password;

}
