package com.example.reactive.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Person {
    private Integer id;

    private String firstName;

    private String lastName;
}
