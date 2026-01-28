package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.TestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestInfoRepo extends JpaRepository<TestInfo,String > {

}
