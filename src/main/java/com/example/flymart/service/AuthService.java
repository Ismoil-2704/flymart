package com.example.flymart.service;

import com.example.flymart.entity.User;
import com.example.flymart.repository.UserRepo;
import com.example.flymart.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordUtil passwordUtil;

    public boolean login(String usernameOrEmail, String password) {
        Optional<User> user = userRepo.findByUsernameOrEmail(usernameOrEmail);
        if (user.isEmpty()) {
            return false;
        }
        return passwordUtil.verifyPassword(password, user.get().getPassword(), user.get().getAlgorithm());
    }
}
