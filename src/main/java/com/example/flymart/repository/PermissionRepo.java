package com.example.flymart.repository;

import com.example.flymart.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepo extends JpaRepository<Permission,Long> {
    Optional<Permission> findByKey(String permissionKey);
    @Query("""
        select p 
        from Permission p
        where p.key in (:keys)
""")
    List<Permission> findAllByKey(@Param("keys") List<String> keys);
}
