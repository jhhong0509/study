package jhhong.guru.netflux.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Movie {

    private String id;

    @NonNull
    private String title;

}
