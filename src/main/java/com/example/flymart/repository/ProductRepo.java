package com.example.flymart.repository;

import com.example.flymart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);

    Optional<Product> findByCode(String code);
    List<Product> findByUserId(Long id);
}
