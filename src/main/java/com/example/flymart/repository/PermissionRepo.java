package com.example.flymart.repository;

import com.example.flymart.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepo extends JpaRepository<Permission,Long> {
    Optional<Permission> findByKey(String permissionKey);

    List<Permission> findAllByKey(List<String> keys);
}
