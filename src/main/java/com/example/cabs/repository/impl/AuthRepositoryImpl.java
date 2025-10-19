package com.example.cabs.repository.impl;

import com.example.cabs.dto.LoginResult;
import com.example.cabs.dto.SaltDto;
import com.example.cabs.repository.LoginMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepositoryImpl {
    private final LoginMapper loginMapper;

    public AuthRepositoryImpl(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    public byte[] getUserSaltByEmail(String email) {
        SaltDto dto = loginMapper.getUserSaltByEmail(email);
        return dto == null ? null : dto.getSalt();
    }

    public LoginResult login(String email, byte[] passwordHash) {
        return loginMapper.login(email, passwordHash);
    }
}
