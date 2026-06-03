package com.raheemspence.service;

import com.raheemspence.dto.LoginRequest;
import com.raheemspence.dto.SignupRequest;
import com.raheemspence.model.User;
import com.raheemspence.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    This annotation is for business logic. It marks a class as a Spring managed business logic component that Spring
    automatically creates and injects where needed, otherwise we would have have to manually do new AuthService() because
    Spring would ignore this class without the annotation.
 */
@Service
public class AuthService {

    /*
        We use final for safety because we dont want our repo to accidentally change like userRepository = null or
        something. Using final makes this dependency fixed for the lifetime of the service.

        Spring automatically provides objects (dependencies) to a class instead of a class creating them manually aka
        dependency injection
     */
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Signup Logic
    public String signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists";
        }
        if(!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match";
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPasswordHash(hashedPassword);

        userRepository.save(user);

        return "User created successfully";
    }

    // Login logic
    public Optional<User> login(LoginRequest request) {

        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        // Check is user exists
        if (userOptional.isEmpty()) {
            // This Optional contains nothing, heres a box that is empty on purpose
            return Optional.empty();
        }

        // if user exists, compare passwords
        User user = userOptional.get();

        if(passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            // Optional.of means im creating an Optional (container) that definitely contains a value so it must not be null
            // In other words, its like saying here is a box and i guarantee there is something inside it
            return Optional.of(user);
        }

        return Optional.empty();

    }

}
