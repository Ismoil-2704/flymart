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
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
    Optional<User> findByUsernameOrEmailAndPassword(String usernameOrEmail,String password);

    Optional<User> findByUserName(String username);

    @Query("""
                        select u.user_name,r.name from users u
                        left join user_region ur on ur.user_id = u.id
                        left join region r on r.id = ur.region_id
                        left join role r on r.id = u.role_id
                        where ur.region_id = :region_id and r.name =:role_name
            """)
    Optional<User> findByRegionAndRole(@Param("region_id") Long regionId,
                                       @Param("role_name") String roleName);
}
