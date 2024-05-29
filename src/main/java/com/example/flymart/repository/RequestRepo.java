package com.example.flymart.repository;

import com.example.flymart.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepo extends JpaRepository<Request,Long> {
    Optional<Request> findByCode(String requestCode);
}
