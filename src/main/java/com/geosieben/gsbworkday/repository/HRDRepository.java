package com.geosieben.gsbworkday.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.HRDTickets;

public interface HRDRepository extends JpaRepository<HRDTickets,Integer>{
@Query (value = "SELECT * FROM hrdtickets where raisedBy=:eid order by id desc",nativeQuery = true)
public List<HRDTickets> findHrdTicketsByRequester(@Param("eid") String eid);
}
