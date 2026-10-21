
package com.medvault;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(
            UserService userService,
            AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(
            @Valid @RequestBody User user) {

        userService.registerUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "User registered successfully"
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(
            @Valid @RequestBody LoginRequest request) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.email().trim(),
                                    request.password()
                            )
                    );

            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok(Map.of(
                        "message", "Login successful"
                ));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }
    }

    public record LoginRequest(
            @NotBlank(message = "Email cannot be blank")
            @Email(message = "Please provide a valid email address")
            String email,

            @NotBlank(message = "Password cannot be blank")
            String password
    ) {
    }
}
