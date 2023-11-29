package com.m2.bookstore.utils;

import com.m2.bookstore.common.logger.CustomLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */
public final class PasswordEncoderUtils implements CustomLogger {

    public static String hashPassword(String password) {
        try {
            // Hash the password without using a salt
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = digest.digest(password.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedPassword) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private PasswordEncoderUtils() {
    }
}
