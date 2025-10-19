package com.example.cabs.dto;

public class SaltDto {
    private byte[] salt;

    public SaltDto() {}

    public byte[] getSalt() { return salt; }
    public void setSalt(byte[] salt) { this.salt = salt; }
}
