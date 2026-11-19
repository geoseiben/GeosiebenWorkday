package com.geosieben.gsbworkday.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.geosieben.gsbworkday.entity.AssetLease;

public interface AssetLeaseRepository extends JpaRepository<AssetLease,String>{
@Query(value="select * from assetlease order by createdAt desc limit 1",nativeQuery=true)
Optional<AssetLease> getMaxAssignmentId();
}
