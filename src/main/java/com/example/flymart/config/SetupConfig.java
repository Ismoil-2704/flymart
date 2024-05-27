package com.example.flymart.config;

import com.example.flymart.entity.Permission;
import com.example.flymart.entity.Role;
import com.example.flymart.repository.PermissionRepo;
import com.example.flymart.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SetupConfig {

    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;

    @Bean
    public CommandLineRunner createDefaultRoles() {
        return args -> {
            createPermissionIfNotFound("USER_READ");
            createPermissionIfNotFound("PRODUCT_READ");
            createPermissionIfNotFound("PLACE_CREATE");
            createPermissionIfNotFound("PLACE_EDIT");
            createPermissionIfNotFound("PLACE_DELETE");
            createPermissionIfNotFound("REGION_CREATE");
            createPermissionIfNotFound("REGION_EDIT");
            createPermissionIfNotFound("REGION_DELETE");
            createPermissionIfNotFound("OFFER_CREATE");
            createPermissionIfNotFound("OFFER_READ");
            createPermissionIfNotFound("REQUEST_CREATE");
            createPermissionIfNotFound("REQUEST_READ");
            createPermissionIfNotFound("PERMISSION_READ");
            createPermissionIfNotFound("TRANSACTION_READ");
            createPermissionIfNotFound("TRANSACTION_CREATE");
            createPermissionIfNotFound("TRANSACTION_UPDATE");
            createPermissionIfNotFound("TRANSACTION_DELETE");
            createRoleIfNotFound("ADMIN");
            createRoleIfNotFound("EDITOR");
            createRoleIfNotFound("READER");
            createRoleIfNotFound("CARRIER");
        };
    }

    private void createRoleIfNotFound(String roleName) {
        roleRepo.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            if (roleName.equals("ADMIN")){
                role.setName(roleName);
                role.setPermissions(permissionRepo.findAll());
            } else if (roleName.equals("CARRIER")) {
                role.setName(roleName);
                role.setPermissions(permissionRepo.findAllByKey(List.of("TRANSACTION_READ","TRANSACTION_CREATE","TRANSACTION_UPDATE","TRANSACTION_DELETE")));
            }
            return roleRepo.save(role);
        });
    }

    private void createPermissionIfNotFound(String permissionKey) {
        permissionRepo.findByKey(permissionKey).orElseGet(() -> {
            Permission permission = new Permission();
            permission.setKey(permissionKey);
            return permissionRepo.save(permission);
        });
    }
}
