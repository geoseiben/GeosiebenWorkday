package com.geosieben.gsbworkday.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.Vendor;

import java.util.Optional;
import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<com.geosieben.gsbworkday.entity.Vendor, Integer> {

    // Common query derived methods
    Optional<com.geosieben.gsbworkday.entity.Vendor> findByEmail(String email);

    List<Vendor> findByVendorNameContainingIgnoreCase(String vendorName);

    boolean existsByEmail(String email);
}
