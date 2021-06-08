package com.first.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @Value(value = "${db_url}")
    private String dbUrl;

    @GetMapping
    public String aa() {
        System.out.println(dbUrl);
        return dbUrl;
    }
}
