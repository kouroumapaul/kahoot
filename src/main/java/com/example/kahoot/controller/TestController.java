package com.example.kahoot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Test")
public class TestController {

    @GetMapping("/public/test")
    public String publicTest() {
        return "Public endpoint works!";
    }
}