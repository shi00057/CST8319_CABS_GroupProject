package com.example.cabs.repository;

import com.example.cabs.dto.LoginResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {
    String getUserSaltByEmail(@Param("p_Email") String email);
    LoginResult login(@Param("p_Email") String email);
}
