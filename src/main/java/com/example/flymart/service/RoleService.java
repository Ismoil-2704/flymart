package com.example.flymart.service;

import com.example.flymart.entity.Permission;
import com.example.flymart.entity.Role;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqRoleCreate;
import com.example.flymart.model.ReqRoleUpdate;
import com.example.flymart.repository.PermissionRepo;
import com.example.flymart.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;

    public void create(ReqRoleCreate roleCreate) throws DataExistsExceptions {
        Optional<Role> optional = roleRepo.findByName(roleCreate.getName());
        if (optional.isPresent()) {
            throw new DataExistsExceptions(ServerCode.ROLE_EXIST.message);
        }
        Role role = new Role();
        roleToSave(role, roleCreate.getName(), roleCreate.getDescription(),roleCreate.getPermissions());
    }

    public void update(ReqRoleUpdate roleUpdate) throws DataNotFoundExceptions {
        Optional<Role> optional = roleRepo.findById(roleUpdate.getId());
        if (optional.isEmpty()) {
            throw new DataNotFoundExceptions(ServerCode.ROLE_NOT_FOUND.message);
        }
        Role role = optional.get();
        roleToSave(role, roleUpdate.getName(), roleUpdate.getDescription(),roleUpdate.getPermissions());
    }

    public void delete(Long roleId) throws DataNotFoundExceptions {
        Optional<Role> optional = roleRepo.findById(roleId);
        if (optional.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.ROLE_NOT_FOUND.message);
        }
        roleRepo.delete(optional.get());
    }

    private void roleToSave(Role role, String name, String description, Set<Long> permissions) {
        role.setName(name);
        role.setPermissions(permissionRepo.findAllById(permissions));
        role.setDescription(description);
    }

    @Transactional
    public void createRoleIfNotFound(String roleName) {
        roleRepo.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            if ("ADMIN".equals(roleName)) {
                role.setPermissions(permissionRepo.findAll());
            } else if ("CARRIER".equals(roleName)) {
                role.setPermissions(permissionRepo.findAllByKey(List.of(
                        "TRANSACTION_READ",
                        "TRANSACTION_CREATE",
                        "TRANSACTION_UPDATE",
                        "TRANSACTION_DELETE"
                )));
            }
            return roleRepo.save(role);
        });
    }

    @Transactional
    public void createPermissionIfNotFound(String permissionKey) {
        permissionRepo.findByKey(permissionKey).orElseGet(() -> {
            Permission permission = new Permission();
            permission.setKey(permissionKey);
            return permissionRepo.save(permission);
        });
    }
}
