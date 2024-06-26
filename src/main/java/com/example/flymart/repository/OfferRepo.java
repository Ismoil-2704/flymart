package com.example.flymart.repository;

import com.example.flymart.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepo extends JpaRepository<Offer,Long> {
    Optional<Offer> findByCode(String offerCode);

    List<Offer> findByUserId(Long id);
}
