package com.example.cabs.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserLookupMapper {
    Integer findUserIdByLogin(@Param("login") String login);
}
