package com.geosieben.gsbworkday.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.*;
import java.util.List;


@Repository
public interface RootProjectRepository extends JpaRepository<RootProject, Integer> {

    @Query(value = "SELECT * FROM rootprojects WHERE category = ?1 AND clientId = ?2", nativeQuery = true)
    RootProject findExistingRoot(int category, int client);
 @Query(
      value = "SELECT * FROM rootprojects WHERE clientId=?1 AND projectType='Pilot'",
      nativeQuery = true
    )
    RootProject findPilotProjectsByClientId(int clientId);

@Query(value = "SELECT * FROM rootprojects where clientId=?1 and projectType='Project'",nativeQuery = true)
    List<RootProject> findProjectsByClientId(int  clientid);

}

