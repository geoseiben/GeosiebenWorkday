package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.DocumentRequest;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest,Integer>{
@Query (value = "SELECT concat(e.firstname,' ',e.lastName) as requester,concat(a.firstname,' ',a.lastName) as actionBy,"+
" date_format(d.requestedOn,'%d-%b-%Y') as requestedOn ,date_format(d.deliveryDate,'%d-%b-%Y') as requestedOn,"+
"d.reason,d.requestedDocument,d.status,d.id,d.adminRemarks,d.documentPath "+
"FROM documentrequests d "+
"LEFT JOIN employee_basic_info e on e.eid=d.requester "+
"LEFT JOIN employee_basic_info a on a.eid=d.actionBy where d.requester=:requester",nativeQuery = true)
public List<DocuRequestProjection> getMyDocRequestProjections(@Param("requester") String requester);
}
