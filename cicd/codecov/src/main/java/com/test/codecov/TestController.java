package com.test.codecov;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String hello(@RequestParam String code) {
        if (code.equals("aa")) {
            return "not hello";
        }
        return "hello";
    }
}
