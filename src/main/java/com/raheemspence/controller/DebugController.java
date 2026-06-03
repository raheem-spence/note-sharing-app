package com.raheemspence.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    @GetMapping("/api/test")
    public String test(Authentication authentication) {

        System.out.println("🔥 AUTH OBJECT: " + authentication);

        if (authentication == null) {
            return "NOT AUTHENTICATED";
        }

        return authentication.getName();
    }
}

