package com.example.cabs.service.impl;

import com.example.cabs.dto.LoginRequest;
import com.example.cabs.dto.LoginResult;
import com.example.cabs.repository.impl.AuthRepositoryImpl;
import com.example.cabs.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthRepositoryImpl repo;

    public AuthServiceImpl(AuthRepositoryImpl repo) {
        this.repo = repo;
    }

    @Override
    public String getSalt(String email) {
        return repo.getUserSaltByEmail(email);
    }

    @Override
    public LoginResult login(LoginRequest request) {
        return repo.login(request.getEmail());
    }
}
