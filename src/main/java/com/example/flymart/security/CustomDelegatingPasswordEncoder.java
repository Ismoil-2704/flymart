package com.example.flymart.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class CustomDelegatingPasswordEncoder implements PasswordEncoder {

    private static final String BCRYPT_PREFIX = "{bcrypt}";
    private static final String SCRYPT_PREFIX = "{scrypt}";

    private final Map<String, PasswordEncoder> encoders = new HashMap<>();
    private final PasswordEncoder defaultEncoder;

    public CustomDelegatingPasswordEncoder(PasswordEncoder defaultEncoder) {
        this.defaultEncoder = defaultEncoder;
        this.encoders.put(BCRYPT_PREFIX, defaultEncoder);
        this.encoders.put(SCRYPT_PREFIX, new SCryptPasswordEncoder(
                16384, 8, 1, 32, 64));
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return BCRYPT_PREFIX + defaultEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword.startsWith(BCRYPT_PREFIX)) {
            return encoders.get(BCRYPT_PREFIX).matches(rawPassword, encodedPassword.substring(BCRYPT_PREFIX.length()));
        } else if (encodedPassword.startsWith(SCRYPT_PREFIX)) {
            return encoders.get(SCRYPT_PREFIX).matches(rawPassword, encodedPassword.substring(SCRYPT_PREFIX.length()));
        } else {
            throw new IllegalArgumentException("Invalid password encoding format");
        }
    }
}

