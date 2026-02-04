
package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeJoiningInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoiningInfoRepository extends JpaRepository<EmployeeJoiningInfo,String> {
    EmployeeJoiningInfo  findByEmployeeBasicInfo_EID(String eid);
}


