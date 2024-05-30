package com.example.flymart.repository;

import com.example.flymart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUserNameOrEmailAndPassword(String usernameOrEmail,String email,String password);

    Optional<User> findByUserName(String username);

    @Query("""
        select u 
        from User u
        left join u.regions rg
        left join u.role ro
        where rg.id = :region_id and ro.name = :role_name
    """)
    Optional<User> findByRegionAndRole(@Param("region_id") Long regionId,
                                       @Param("role_name") String roleName);
}
