package com.example.flymart.controller;

import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody ReqAuth reqAuth){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(authService.login(reqAuth), new HashMap<>()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new HashMap<>(), e.getMessage()));
        }
    }
}
