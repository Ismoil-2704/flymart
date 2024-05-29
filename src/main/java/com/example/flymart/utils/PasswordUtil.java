package com.example.flymart.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class PasswordUtil {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    public String hashPassword(String password, String algorithm) {
        if ("bcrypt".equalsIgnoreCase(algorithm)) {
            return bCryptPasswordEncoder.encode(password);
        } else if ("scrypt".equalsIgnoreCase(algorithm)) {
            return sCryptPasswordEncoder.encode(password);
        } else {
            throw new IllegalArgumentException("Unsupported hashing algorithm");
        }
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword, String algorithm) {
        if ("bcrypt".equalsIgnoreCase(algorithm)) {
            return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
        } else if ("scrypt".equalsIgnoreCase(algorithm)) {
            return sCryptPasswordEncoder.matches(rawPassword, encodedPassword);
        } else {
            throw new IllegalArgumentException("Unsupported hashing algorithm");
        }
    }
}
