package com.geosieben.gsbworkday.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.entity.ProjectDelivery;
public interface ProjectDeliveryRepository extends JpaRepository<ProjectDelivery,Integer> {
    @Query (value = "SELECT * FROM projectdelivery where projectId=:pid and feeder=:feeder",nativeQuery = true)
    public Optional<ProjectDelivery> getDeliveryDataByPidFeeder(@Param ("pid") int pid,@Param ("feeder") String feeder);

}
