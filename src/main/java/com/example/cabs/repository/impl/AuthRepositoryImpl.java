package com.example.cabs.repository.impl;

import com.example.cabs.dto.LoginResult;
import com.example.cabs.repository.LoginMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepositoryImpl {
    private final LoginMapper loginMapper;

    public AuthRepositoryImpl(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    public String getUserSaltByEmail(String email) {
        return loginMapper.getUserSaltByEmail(email);
    }

    public LoginResult login(String email) {
        return loginMapper.login(email);
    }
}
