package com.geosieben.gsbworkday.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.geosieben.gsbworkday.entity.Project;
import com.geosieben.gsbworkday.entity.ProjectAllocation;
import com.geosieben.gsbworkday.repository.AllocationRepository;
import com.geosieben.gsbworkday.repository.ProjectRepository;

@Service
public class ExcelService {
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/employee/";
    private AllocationRepository repository;
    private ProjectRepository projectRepository;

    
    public ExcelService(AllocationRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

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

    public String uploadImage(MultipartFile file){

        if (file.isEmpty()) {
           // return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        try {
            // Ensure directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save file
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(path, bytes);

          //  return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
           // return ResponseEntity.internalServerError().body("Failed to upload: " + e.getMessage());
        }
        return  "uploads/employee/"+ file.getOriginalFilename();
    }

    
}
