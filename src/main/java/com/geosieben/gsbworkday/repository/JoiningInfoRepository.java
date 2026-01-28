
package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.EmployeeJoiningInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface JoiningInfoRepository extends JpaRepository<EmployeeJoiningInfo,String> {
}
