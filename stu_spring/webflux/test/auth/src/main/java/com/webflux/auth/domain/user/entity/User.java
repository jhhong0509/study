package com.webflux.auth.domain.user.entity;

import com.mongodb.lang.NonNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document
public class User {

    @Id
    private final String id;

    @NonNull
    @Indexed(unique = true)
    private final String email;

    @NonNull
    private final String password;

}
