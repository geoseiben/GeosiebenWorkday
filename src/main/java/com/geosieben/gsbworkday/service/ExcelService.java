package com.geosieben.gsbworkday.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.geosieben.gsbworkday.entity.Project;
import com.geosieben.gsbworkday.entity.ProjectAllocation;
import com.geosieben.gsbworkday.repository.AllocationRepository;
import com.geosieben.gsbworkday.repository.ProjectRepository;

@Service
public class ExcelService {
    @Autowired
    private AllocationRepository repository;
    @Autowired
    private ProjectRepository projectRepository;

    public void saveExcelData(MultipartFile file,int projectid) throws IOException {
        List<ProjectAllocation> products = new ArrayList<>();
        Project project=projectRepository.findById(projectid).orElse(null);
project.setStatus(1);
Project updated=(Project)projectRepository.save(project);
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = (Sheet) workbook.getSheetAt(0); // Get first sheet

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row

            ProjectAllocation p = new ProjectAllocation();
            double prohrs = row.getCell(2).getNumericCellValue();
            double qcHrs = row.getCell(3).getNumericCellValue();
            double deliveryHours = row.getCell(4).getNumericCellValue();


            p.setFeederAlloted(row.getCell(1).getStringCellValue());
            p.setHrsAssigned(BigDecimal.valueOf(prohrs));
            p.setQcHrs(BigDecimal.valueOf(qcHrs));
            p.setDeliveryHours(BigDecimal.valueOf(deliveryHours));
            p.setProject(updated);
            products.add(p);
        }
        
        repository.saveAll(products);
        workbook.close();
    }
}
