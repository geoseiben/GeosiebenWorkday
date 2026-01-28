package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
    User findByEmployeeBasicInfo_EID(String eid);
}
