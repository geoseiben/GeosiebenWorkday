package com.geosieben.gsbworkday.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.geosieben.gsbworkday.entity.ProjectCategories;
import com.geosieben.gsbworkday.repository.ProjectcategoryRepository;
@Service
public class CategoryService implements CategoryInterface{
    @Autowired
    private ProjectcategoryRepository projectcategoryRepository;
    @Override
    public ResponseEntity<Map<String, String>> addCategory(String categoryname, String categoryshortname) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCategory'");
    }

    @Override
    public List<ProjectCategories> getAllCategories() {
    return projectcategoryRepository.findAll();
    }

}
