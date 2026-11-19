package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
}
