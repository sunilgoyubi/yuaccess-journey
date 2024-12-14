package com.yubi.yuaccessjourney.controller;

import com.yubi.yuaccessjourney.model.User;

import com.yubi.yuaccessjourney.security.JwtTokenProvider;
import com.yubi.yuaccessjourney.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * @param user The user information for signup.
     * @return Response indicating the success or failure of the signup.
     */
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(409).body(Map.of("status", "failure", "message", "Email already in use"));
        }

        userService.save(user);
        return ResponseEntity.status(201).body(Map.of("status", "success", "message", "User registered successfully"));
    }

    /**
     * @param credentials The user's email and password.
     * @return Response indicating the result of the login attempt.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        Optional<User> authenticatedUser = userService.authenticate(email, password);

        if (authenticatedUser.isPresent()) {
            try {
                String token = jwtTokenProvider.createToken(email);
                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "message", "Login successful",
                        "token", token
                ));
            } catch (Exception e) {
                return ResponseEntity.status(500).body(Map.of(
                        "status", "failure",
                        "message", "Error generating token"
                ));
            }
        } else {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "failure",
                    "message", "Invalid credentials"
            ));
        }
    }
}
