package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.entity.Department;
import com.geosieben.gsbworkday.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DepartmentService implements DepartmentInterface{
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public ResponseEntity<Map<String, String>> addDepartment(String departmentname, String departmentShortName, String deptCode) {
        Department department=new Department(departmentname,departmentShortName,deptCode);
        departmentRepository.save(department);
        Map<String,String> response=new HashMap<>();
        response.put("status","success");
        response.put("message","Department Added Successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }
}
