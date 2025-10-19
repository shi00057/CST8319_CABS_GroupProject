package com.example.cabs.service;

import com.example.cabs.dto.LoginRequest;
import com.example.cabs.dto.LoginResult;

public interface AuthService {
    String getSalt(String email);
    LoginResult login(LoginRequest request);
}
