package com.example.flymart.repository;

import com.example.flymart.entity.Transactions;
import com.example.flymart.payload.ProductTransactionCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transactions,Long> {
    Optional<Transactions> findByRequestCodeAndOfferCode(String requestCode,String offerCode);

    Optional<Transactions> findByUserId(Long userId);

    Optional<Transactions> findByCode(String code);

    @Query("SELECT new com.example.flymart.payload.ProductTransactionCount(pt.id, COUNT(t)) " +
            "FROM Transactions t " +
            "JOIN t.products pt " +
            "GROUP BY pt.id " +
            "HAVING COUNT(t) > 0 " +
            "ORDER BY pt.id")
    List<ProductTransactionCount> findProductTransactionCounts();
}
