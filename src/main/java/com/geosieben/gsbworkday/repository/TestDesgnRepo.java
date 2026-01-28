package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.TestDesgn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDesgnRepo extends JpaRepository<TestDesgn,String> {
}
