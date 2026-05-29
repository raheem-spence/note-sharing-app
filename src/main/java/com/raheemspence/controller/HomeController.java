package com.raheemspence.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String hello() {
        return "Hello, world!";
    }

    @GetMapping("/profile")
    public String helloRaheem() {
        return "Hello, Raheem!";
    }
}
