package com.budgetvault.budgetvault.service;

import com.budgetvault.budgetvault.entity.User;
import com.budgetvault.budgetvault.dto.LoginRequest;
import com.budgetvault.budgetvault.dto.LoginResponse;
import com.budgetvault.budgetvault.dto.UserResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userService
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "Invalid email or password"
                        )
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPasswordHash()
                );

        if (!passwordMatches) {
            throw new BadCredentialsException(
                    "Invalid email or password"
            );
        }

        String accessToken =
                jwtService.generateAccessToken(
                        user.getId().toString(),
                        user.getEmail()
                );

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setLanguage(user.getLanguage());
        userResponse.setBaseCurrency(user.getBaseCurrency());
        userResponse.setCreatedAt(user.getCreatedAt());

        return new LoginResponse(
                accessToken,
                "Bearer",
                userResponse
        );
    }
}