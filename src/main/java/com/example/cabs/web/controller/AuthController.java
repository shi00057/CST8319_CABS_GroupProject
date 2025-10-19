package com.example.cabs.web.controller;

import com.example.cabs.dto.LoginRequest;
import com.example.cabs.dto.LoginResult;
import com.example.cabs.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public Map<String, String> getSalt(@RequestParam String email) {
        String salt = authService.getSalt(email);
        return Collections.singletonMap("salt", salt);
    }

    @PostMapping("/login")
    public LoginResult login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }
}
