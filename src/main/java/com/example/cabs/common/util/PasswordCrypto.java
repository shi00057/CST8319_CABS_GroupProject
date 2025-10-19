package com.example.cabs.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public final class PasswordCrypto {

    private static final SecureRandom RNG;
    static {
        SecureRandom tmp;
        try {
            tmp = SecureRandom.getInstanceStrong();
        } catch (Exception e) {
            tmp = new SecureRandom();
        }
        RNG = tmp;
    }

    /** Generate cryptographic salt with the given length (in bytes). */
    public static byte[] generateSalt(int length) {
        byte[] salt = new byte[length];
        RNG.nextBytes(salt);
        return salt;
    }

    /** MySQL-side algorithm: SHA256( HEX(salt) + rawPassword ), returns 32-byte binary. */
    public static byte[] mysqlHash(byte[] salt, String rawPassword) {
        String saltHex = toHex(salt);
        byte[] input = (saltHex + rawPassword).getBytes(StandardCharsets.UTF_8);
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

    private PasswordCrypto() {}
}
