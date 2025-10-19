package com.example.cabs.service.impl;

import com.example.cabs.common.util.PasswordCrypto;
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
        byte[] salt = repo.getUserSaltByEmail(email);
        return salt == null ? null : PasswordCrypto.toHex(salt);
    }

    @Override
    public LoginResult login(LoginRequest request) {
        byte[] salt = repo.getUserSaltByEmail(request.getEmail());
        if (salt == null) return null;
        byte[] hash = PasswordCrypto.mysqlHash(salt, request.getPassword());
        return repo.login(request.getEmail(), hash);
    }
}
