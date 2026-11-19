package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
    User findByEmployeeBasicInfo_EID(String eid);
}
