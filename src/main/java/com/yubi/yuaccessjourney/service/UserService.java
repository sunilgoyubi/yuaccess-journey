package com.yubi.yuaccessjourney.service;

import com.yubi.yuaccessjourney.repository.UserRepository;
import com.yubi.yuaccessjourney.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Use PasswordEncoder instead of BCryptPasswordEncoder

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Find user by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Save user (hashing the password before saving)
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Hash password before saving
        userRepository.save(user);
    }

    // Authenticate user
    public Optional<User> authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}
