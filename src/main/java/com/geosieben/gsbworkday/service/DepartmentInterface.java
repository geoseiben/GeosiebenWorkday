package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.entity.Department;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface DepartmentInterface {
    public ResponseEntity<Map<String,String>> addDepartment(String departmentname, String departmentShortName, String deptCode);
    public List<Department> getDepartments();
}
