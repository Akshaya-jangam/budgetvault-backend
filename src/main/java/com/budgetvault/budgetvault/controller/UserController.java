package com.budgetvault.budgetvault.controller;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budgetvault.budgetvault.dto.RegisterRequest;
import com.budgetvault.budgetvault.dto.UserResponse;
import com.budgetvault.budgetvault.entity.User;
import com.budgetvault.budgetvault.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = userService.registerUser(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getLanguage(),
                request.getBaseCurrency()
        );

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setLanguage(user.getLanguage());
        response.setBaseCurrency(user.getBaseCurrency());
        response.setCreatedAt(user.getCreatedAt());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/me")
public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {

    UUID userId = UUID.fromString(authentication.getName());

    User user = userService.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    UserResponse response = new UserResponse();

    response.setId(user.getId());
    response.setName(user.getName());
    response.setEmail(user.getEmail());
    response.setLanguage(user.getLanguage());
    response.setBaseCurrency(user.getBaseCurrency());
    response.setCreatedAt(user.getCreatedAt());

    return ResponseEntity.ok(response);
}
}