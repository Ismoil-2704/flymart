package com.example.flymart.service;

import com.example.flymart.controller.ReqAuth;
import com.example.flymart.entity.User;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.repository.UserRepo;
import com.example.flymart.security.JwtTokenProvider;
import com.example.flymart.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> login(ReqAuth reqAuth) throws DataNotFoundExceptions {
        String encode = passwordEncoder.encode(reqAuth.getPassword());
        Optional<User> optional = userRepo.findByUsernameOrEmailAndPassword(reqAuth.getUsernameOrEmail(),encode);
        if (optional.isPresent()) {
            User user = optional.get();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            reqAuth.getUsernameOrEmail(),
                            reqAuth.getPassword()
                    )
            );
            String jwtToken = jwtTokenProvider.generateToken(user);
            HashMap<String, Object> map = new HashMap<>();
            map.put("Token", jwtToken);
            return map;
        }
        throw new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message);
    }

    public User me() throws DataNotFoundExceptions {
        return currentUser();
    }
    public User currentUser() throws DataNotFoundExceptions {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String name = authentication.getName();
            user = userRepo.findByUserName(name).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        }
        return user;
    }
}
