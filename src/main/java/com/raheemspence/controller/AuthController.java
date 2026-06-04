package com.raheemspence.controller;

import com.raheemspence.dto.LoginRequest;
import com.raheemspence.dto.SignupRequest;
import com.raheemspence.model.Note;
import com.raheemspence.model.User;
import com.raheemspence.repository.NoteRepository;
import com.raheemspence.repository.UserRepository;
import com.raheemspence.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Means this class handles HTTP requests and returns data (not HTML pages)
@RestController

// Means all routes in this class start with /auth
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request, HttpSession httpSession) {
        Optional<User> userOptional = authService.login(request);

        if (userOptional.isEmpty()) {
            // Login failed
            return "Invalid credentials";
        }
        User user = userOptional.get();
        httpSession.setAttribute("userId", user.getId());

        return "Login success";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

}