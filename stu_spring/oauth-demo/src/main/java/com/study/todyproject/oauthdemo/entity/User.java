package com.study.todyproject.oauthdemo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class User {

    @Id
    private String id;

    private String email;

    private String name;

}
