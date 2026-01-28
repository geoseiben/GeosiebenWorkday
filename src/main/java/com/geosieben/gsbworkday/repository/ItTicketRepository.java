package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.ItTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItTicketRepository extends JpaRepository<ItTicket,Integer> {
    public List<ItTicket> findByEmployeeBasicInfo_EID(String eid);
}
