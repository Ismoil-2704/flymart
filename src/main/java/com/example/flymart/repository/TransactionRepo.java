package com.example.flymart.repository;

import com.example.flymart.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transactions,Long> {
    Optional<Transactions> findByRequestCodeAndOfferCode(Long requestCode,Long offerCode);
}
