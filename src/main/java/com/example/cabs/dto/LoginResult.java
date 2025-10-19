package com.example.cabs.dto;

public class LoginResult {
    private Integer userId;
    private String role;
    private Integer isActive;

    public LoginResult() {}

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Integer getIsActive() { return isActive; }
    public void setIsActive(Integer isActive) { this.isActive = isActive; }
}
