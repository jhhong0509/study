package com.mentoring.secretmanager.controller;

import com.mentoring.secretmanager.test.Test;
import com.mentoring.secretmanager.test.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final TestRepository testRepository;

    @GetMapping("/test")
    public String test(@RequestParam String title) {
        return testRepository.save(
                Test.builder()
                        .title(title)
                        .build()
        ).getTitle();
    }

}
