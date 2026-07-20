package com.raheemspence.controller;

import com.raheemspence.dto.UserResponse;
import com.raheemspence.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");

        if (userId == null) {
            return null;
        }

        return userService.getCurrentUser(userId);
    }
}
