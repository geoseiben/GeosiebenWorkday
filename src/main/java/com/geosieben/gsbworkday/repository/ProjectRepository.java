package com.geosieben.gsbworkday.repository;
import com.geosieben.gsbworkday.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer>{
@Query(value="SELECT * from projects where rootid=?1",nativeQuery=true)
Project findProjectByRootid(int rootid);

}
