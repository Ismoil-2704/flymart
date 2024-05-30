package com.example.flymart.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class PasswordUtil {

    private final PasswordEncoder sCryptPasswordEncoder;

    private final PasswordEncoder bCryptPasswordEncoder;

    public PasswordUtil(@Qualifier("bcryptPasswordEncoder") PasswordEncoder sCryptPasswordEncoder,@Qualifier("scryptPasswordEncoder") PasswordEncoder bCryptPasswordEncoder) {
        this.sCryptPasswordEncoder = sCryptPasswordEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


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
