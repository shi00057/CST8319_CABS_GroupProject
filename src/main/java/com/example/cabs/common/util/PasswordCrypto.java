package com.example.cabs.common.util;

import java.security.MessageDigest;

public final class PasswordCrypto {
    public static byte[] mysqlHash(byte[] salt, String rawPassword) {
        String saltHex = toHex(salt);
        byte[] input = (saltHex + rawPassword).getBytes(java.nio.charset.StandardCharsets.UTF_8);
        return sha256(input);
    }

    public static String toHex(byte[] data) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[data.length * 2];
        for (int j = 0; j < data.length; j++) {
            int v = data[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] sha256(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
