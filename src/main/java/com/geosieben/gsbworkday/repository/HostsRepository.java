package com.geosieben.gsbworkday.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.HostsProjection;
import com.geosieben.gsbworkday.entity.Hosts;

public interface HostsRepository extends JpaRepository<Hosts,Integer>{

    @Query(value = "SELECT  concat(e.firstname,' ',e.lastname) as username,"+
    " concat(lu.firstname,' ',lu.lastname) as leaseUser,e.eid,j.workmail,  h.hostid,"+
    "h.status,  h.assignmentid,  h.leaseid,  al.leaseReturnDate as leaseReturned,  al.leasedTo as leaseEid,"+
    "h.leaseid, al.leaseDocumentPath,aaa.ackFilepath,h.graphics,h.hostname,h.hosttype,h.lanno,  h.processor,"+
    "h.ram,h.graphics,h.os,h.ipAddress,  a.assetid AS monitor1_assetid, aa.assetid AS monitor1pc_assetid,"+
    "ab.assetid AS monitor1vc_assetid,  a1.assetid AS monitor2_assetid,  a1a.assetid AS monitor2pc_assetid,"+
    " a1b.assetid AS monitor2vc_assetid,  a2.assetid AS mouse_assetid,  a3.assetid AS keyboard_assetid,"+
    "a4.assetid AS emitter_assetid,  a4a.assetid AS emitter_charger_assetid,  a4b.assetid AS goggle_assetid,"+
    "a7.assetid AS headphone_assetid,  a9.assetid as ssd_assetid,  a10.assetid as hdd_assetid,    a11.assetid AS cabinet_assetid,"+
    "a11a.assetid AS cabpc_assetid,     a14.assetid AS ad1_assetid,      a15.assetid AS ad2_assetid,       a16.assetid AS ups_assetid,"+
    "CONCAT(a.assetName, CONCAT('(', CONCAT(a.serialNo, ')'))) AS monitor1,   CONCAT(aa.assetName, CONCAT('(', CONCAT(aa.serialNo, ')'))) AS monitor1pc,"+
    "CONCAT(ab.assetName, CONCAT('(', CONCAT(ab.serialNo, ')'))) AS monitor1vc,  CONCAT(a1.assetName, CONCAT('(', CONCAT(a1.serialNo, ')'))) AS monitor2,"+
    "CONCAT(a1a.assetName, CONCAT('(', CONCAT(a1a.serialNo, ')'))) AS monitor2pc,    CONCAT(a1b.assetName, CONCAT('(', CONCAT(a1b.serialNo, ')'))) AS monitor2vc,"+
    "CONCAT(a2.assetName, CONCAT('(', CONCAT(a2.serialNo, ')'))) AS mouse,  CONCAT(a3.assetName, CONCAT('(', CONCAT(a3.serialNo, ')'))) AS keyboard,"+
    "CONCAT(a4.assetName, CONCAT('(', CONCAT(a4.serialNo, ')'))) AS emitter,CONCAT(a4a.assetName, CONCAT('(', CONCAT(a4a.serialNo, ')'))) AS emitter_charger,"+
    "CONCAT(a4b.assetName, CONCAT('(', CONCAT(a4b.serialNo, ')'))) AS goggle,  CONCAT(a7.assetName, CONCAT('(', CONCAT(a7.serialNo, ')'))) AS headphone,"+
    "CONCAT(a11.assetName, CONCAT('(', CONCAT(a11.serialNo, ')'))) AS cabinet,CONCAT(a11a.assetName, CONCAT('(', CONCAT(a11a.serialNo, ')'))) AS cabpc,"+
    "CONCAT(a12.assetName, CONCAT('(', CONCAT(a12.serialNo, ')'))) AS ssd,CONCAT(a13.assetName, CONCAT('(', CONCAT(a13.serialNo, ')'))) AS hdd,"+
    "CONCAT(a14.assetName, CONCAT('(', CONCAT(a14.serialNo, ')'))) AS ad1,CONCAT(a15.assetName, CONCAT('(', CONCAT(a15.serialNo, ')'))) AS ad2,"+
    "CONCAT(a16.assetName, CONCAT('(', CONCAT(a16.serialNo, ')'))) AS ups FROM   hosts h "+
  "left join assetassignment aaa on h.assignmentid=aaa.assignmentid "+
  "left join assetlease al on al.leaseid=h.leaseid "+
 " LEFT JOIN employee_basic_info lu on lu.eid=al.leasedTo "+
 " left join employee_basic_info e on aaa.assignedTo=e.eid "+
"  left join employee_joining_info j on e.eid=j.eid "+
"LEFT JOIN itasset a ON h.monitor1 = a.assetid "+
"LEFT JOIN itasset aa ON h.mon1pc = aa.assetid "+
"LEFT JOIN itasset ab ON h.mon1vc = ab.assetid "+
"LEFT JOIN itasset a1 ON h.monitor2 = a1.assetid "+
"LEFT JOIN itasset a1a ON h.mon2pc = a1a.assetid "+
"LEFT JOIN itasset a1b ON h.mon2vc = a1b.assetid "+
"LEFT JOIN itasset a2 ON h.mouse = a2.assetid "+
"LEFT JOIN itasset a3 ON h.keyboard = a3.assetid "+
"LEFT JOIN itasset a4 ON h.emmitter = a4.assetid "+
"LEFT JOIN itasset a4a ON h.echarger = a4a.assetid "+
"LEFT JOIN itasset a4b ON h.goggle = a4b.assetid "+
"LEFT JOIN itasset a7 ON h.headphone = a7.assetid "+
"LEFT JOIN itasset a9 ON h.ssd = a9.assetid "+
"LEFT JOIN itasset a10 ON h.hdd = a10.assetid "+
"LEFT JOIN itasset a11 ON h.cabinet = a11.assetid "+
"LEFT JOIN itasset a11a ON h.cabpc = a11a.assetid "+
"LEFT JOIN itasset a12 ON h.ssd = a12.assetid "+ 
"LEFT JOIN itasset a13 ON h.hdd = a13.assetid "+
"LEFT JOIN itasset a14 ON h.ad1 = a14.assetid "+
"LEFT JOIN itasset a15 ON h.ad2 = a15.assetid "+
"LEFT JOIN itasset a16 ON h.ups = a16.assetid;",nativeQuery = true)
public List<HostsProjection> getAllHosts();
    @Modifying 
@Query (value = "UPDATE hosts set status = :status,assignmentid=:assignmentid where hostid =:hostid",nativeQuery = true)
public  void hostAssignmentUpdate(@Param ("status") int status,@Param ("assignmentid") String assignmentid,@Param ("hostid") int hostid);
@Query(value = "SELECT * FROM hosts where assignmentid=:assignmentid",nativeQuery = true)
public Optional<Hosts> findHostByAssignmentId(@Param("assignmentid") String assignmentid);

}
