package com.budgetvault.budgetvault.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.budgetvault.budgetvault.entity.User;
import com.budgetvault.budgetvault.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findById(UUID userId) {
    return userRepository.findById(userId);
    }
    public User registerUser(
            String name,
            String email,
            String password,
            String language,
            String baseCurrency) {
            if (userRepository.findByEmail(email).isPresent()) {
        throw new IllegalArgumentException("Email already registered");
    }

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setLanguage(language);
        user.setBaseCurrency(baseCurrency);

        return userRepository.save(user);
    }
}