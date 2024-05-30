package com.example.flymart.config;

import com.example.flymart.entity.Permission;
import com.example.flymart.entity.Role;
import com.example.flymart.repository.PermissionRepo;
import com.example.flymart.repository.RoleRepo;
import com.example.flymart.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SetupConfig {

    private final RoleService roleService;

    @Bean
    public CommandLineRunner createDefaultRoles() {
        return args -> {
            roleService.createPermissionIfNotFound("USER_READ");
            roleService.createPermissionIfNotFound("PRODUCT_READ");
            roleService.createPermissionIfNotFound("PLACE_CREATE");
            roleService.createPermissionIfNotFound("PLACE_EDIT");
            roleService.createPermissionIfNotFound("PLACE_DELETE");
            roleService.createPermissionIfNotFound("REGION_CREATE");
            roleService.createPermissionIfNotFound("REGION_EDIT");
            roleService.createPermissionIfNotFound("REGION_DELETE");
            roleService.createPermissionIfNotFound("OFFER_CREATE");
            roleService.createPermissionIfNotFound("OFFER_READ");
            roleService.createPermissionIfNotFound("REQUEST_CREATE");
            roleService.createPermissionIfNotFound("REQUEST_READ");
            roleService.createPermissionIfNotFound("PERMISSION_READ");
            roleService.createPermissionIfNotFound("TRANSACTION_READ");
            roleService.createPermissionIfNotFound("TRANSACTION_CREATE");
            roleService.createPermissionIfNotFound("TRANSACTION_UPDATE");
            roleService.createPermissionIfNotFound("TRANSACTION_DELETE");
            roleService.createRoleIfNotFound("ADMIN");
            roleService.createRoleIfNotFound("EDITOR");
            roleService.createRoleIfNotFound("READER");
            roleService.createRoleIfNotFound("CARRIER");
        };
    }
}

