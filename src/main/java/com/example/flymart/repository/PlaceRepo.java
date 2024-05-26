package com.example.flymart.repository;

import com.example.flymart.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepo extends JpaRepository<Place,Long> {
    Optional<Place> findByName(String name);
}
