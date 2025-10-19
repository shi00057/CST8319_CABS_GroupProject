package com.example.cabs.dto;

import com.example.cabs.domain.Role;

public class LoginResult {
    private Long userId;
    private Role role;
    private String displayName;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
