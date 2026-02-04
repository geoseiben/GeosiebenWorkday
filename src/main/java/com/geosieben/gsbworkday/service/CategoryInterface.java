package com.geosieben.gsbworkday.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.entity.ProjectCategories;

public interface CategoryInterface {
    public ResponseEntity<Map<String, String>> addCategory(String categoryname,String categoryshortname);
    public List<ProjectCategories> getAllCategories();
}
