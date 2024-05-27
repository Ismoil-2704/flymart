package com.example.flymart.controller;

import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.repository.PermissionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permission")
public class PermissionController {

    private final PermissionRepo permissionRepo;

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public HttpEntity<?> permissions(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseModel(permissionRepo.findAll(),new Object()));
    }
}
