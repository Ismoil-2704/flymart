package com.example.flymart.repository;

import com.example.flymart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
    Optional<User> findByUserName(String username);
}
