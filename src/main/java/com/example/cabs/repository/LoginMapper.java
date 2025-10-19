package com.example.cabs.repository;

import com.example.cabs.dto.LoginResult;
import com.example.cabs.dto.SaltDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
    SaltDto getUserSaltByEmail(@Param("p_Email") String email);
    LoginResult login(@Param("p_Email") String email, @Param("p_PasswordHash") byte[] passwordHash);
}
