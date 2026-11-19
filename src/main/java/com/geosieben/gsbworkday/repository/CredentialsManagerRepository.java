package com.geosieben.gsbworkday.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.CredentialsManager;

public interface CredentialsManagerRepository extends JpaRepository<CredentialsManager,Integer>{

    @Query(value = "SELECT * FROM credentialsmanager where EID=:eid and type=:type",nativeQuery = true)
    Optional<CredentialsManager>  getCredByEidType(@Param("eid") String eid,@Param("type") String type);

}
