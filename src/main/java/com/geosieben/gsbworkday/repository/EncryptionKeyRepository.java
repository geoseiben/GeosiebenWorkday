package com.geosieben.gsbworkday.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.EncryptionKey;

public interface EncryptionKeyRepository extends JpaRepository<EncryptionKey,Integer> {

    @Query(value="SELECT * FROM encryptionkey where eid=:eid and type=:type",nativeQuery=true)
    public Optional<EncryptionKey> getCredByEidType(@Param("eid") String eid,@Param("type") String type);

}
