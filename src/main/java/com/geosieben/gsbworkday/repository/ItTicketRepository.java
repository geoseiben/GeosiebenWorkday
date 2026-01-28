package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.dto.ActiveTickets;
import com.geosieben.gsbworkday.entity.ItTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItTicketRepository extends JpaRepository<ItTicket,Integer> {
    public List<ItTicket> findByEmployeeBasicInfo_EID(String eid);
@Query("SELECT new com.geosieben.gsbworkday.dto.ActiveTickets(" +
       "t.ticketid, t.issue, t.description, t.priority, " +
       "t.hostname, t.anyDesk, CONCAT(e.firstName, ' ', e.lastName), " +
       "t.status) " +
       "FROM ItTicket t " +
       "LEFT JOIN t.employeeBasicInfo e where t.status<> 3 ")
List<ActiveTickets> activeTickets();

}
