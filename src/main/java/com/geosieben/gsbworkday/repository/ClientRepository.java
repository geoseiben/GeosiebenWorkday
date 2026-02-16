package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.dto.ClientWiseProjectResponseDto;
import com.geosieben.gsbworkday.entity.Clients;
@Repository
public interface ClientRepository extends JpaRepository<Clients,Integer> {
@Query("SELECT new com.geosieben.gsbworkday.dto.ClientWiseProjectResponseDto(" +
             "c.cid, " +
           "c.clientId, " +
           "c.clientName, " +
           "SUM(CASE WHEN rp.projectStatus = 0 THEN 1L ELSE 0L END), " + // Active
           "SUM(CASE WHEN rp.projectStatus = 2 THEN 1L ELSE 0L END), " + // Finished
           "SUM(CASE WHEN rp.projectStatus = 3 THEN 1L ELSE 0L END), " + // Withdrawn
           "COUNT(rp)) " +                                             // Total (COUNT *)
           "FROM RootProject rp " +
           "JOIN rp.client c " + 
           "GROUP BY c.cid,c.clientId, c.clientName")
    List<ClientWiseProjectResponseDto> getClientWiseProjectStats();
}
