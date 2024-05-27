package com.example.flymart.repository;

import com.example.flymart.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {
    Optional<Address> findByNameAndLongitudeAndLatitude(String name, Double longitude, Double latitude);
}
