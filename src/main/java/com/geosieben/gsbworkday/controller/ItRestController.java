package com.geosieben.gsbworkday.controller;

import com.geosieben.gsbworkday.dto.AssetResponseDto;
import com.geosieben.gsbworkday.dto.HostRequestDto;
import com.geosieben.gsbworkday.dto.HostsProjection;
import com.geosieben.gsbworkday.dto.SubscriptionsProjection;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.ItAsset;
import com.geosieben.gsbworkday.entity.Vendor;
import com.geosieben.gsbworkday.repository.HostsRepository;
import com.geosieben.gsbworkday.repository.VendorRepository;
import com.geosieben.gsbworkday.service.CredentialsManagerService;
import com.geosieben.gsbworkday.service.ItService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/it")
public class ItRestController {

    private final ItService itService;
    private final CredentialsManagerService credentialsManagerService;
    private final HostsRepository hostsRepository;
    private final VendorRepository vendorRepository;

    @Value("${app.upload.dir:${user.home}/uploads/invoices}")
    private String uploadDir;

    public ItRestController(ItService itService, 
                            CredentialsManagerService credentialsManagerService, 
                            HostsRepository hostsRepository,VendorRepository vendorRepository) {
        this.itService = itService;
        this.credentialsManagerService = credentialsManagerService;
        this.hostsRepository = hostsRepository;
        this.vendorRepository=vendorRepository;
    }

    @GetMapping("/validateSerialNo")
    public ResponseEntity<?> validateSerialNo(@RequestParam String serialNo) {
        return itService.validateSerialNo(serialNo);
    }

    @PostMapping("/saveAsset")
    public ResponseEntity<?> saveAsset(@RequestParam String assetName,
                                       @RequestParam String brand,
                                       @RequestParam String type,
                                       @RequestParam String category,
                                       @RequestParam String model,
                                       @RequestParam String serialNo,
                                       @RequestParam Integer vendor,
                                        @RequestParam String batchId
                                    ) {
        return itService.addAsset(assetName, category, model, serialNo, vendor,brand,type,batchId);
    }

    @GetMapping("/getAssets")
    public List<ItAsset> getAssets() {
        return itService.getAllAssets();
    }

    @PostMapping("/updateAsset")
    public ResponseEntity<?> updateAsset(@RequestParam("assetId") int assetId,
                                         @RequestParam("assetName") String assetName,
                                         @RequestParam("category") String category,
                                         @RequestParam("model") String model,
                                         @RequestParam("serialNo") String serialNo,
                                         @RequestParam("vendor") String vendor) {
        return itService.updateAsset(assetId, assetName, category, model, serialNo, vendor);
    }

    @GetMapping("/getAssignee")
    public List<EmployeeBasicInfo> getAssignee() {
        return itService.getMembers();
    }

    @PostMapping("/assignAsset")
    public ResponseEntity<Map<String, String>> assignAsset(@RequestParam("assetId") int assetId,
                                                          @RequestParam("employeeId") String employeeId,
                                                          @RequestParam("remarks") String remarks) {
        return itService.assignAssset(assetId, employeeId, remarks);
    }

    @GetMapping("/getCategoryAssets")
    public List<AssetResponseDto> getCategoryAssets(@RequestParam("asset") String asset,
                                                    @RequestParam("newVal") int newVal) {
        return itService.getAssets(asset, newVal);
    }
    @GetMapping("/getVendors")
    public List<Vendor> getVendors() {
        return vendorRepository.findAll();
    }
    

    @GetMapping("/authenticateCmanager")
    public ResponseEntity<Map<String, Boolean>> authenticateUserManager(@RequestParam("password") String password)
            throws NoSuchAlgorithmException {
        return credentialsManagerService.authenticateUser(password);
    }

    @PostMapping("/addHosts")
    public ResponseEntity<Map<String, String>> addHost(HostRequestDto dto) {
        return itService.addHost(dto);
    }

    @GetMapping("/getHosts")
    public List<HostsProjection> getHosts() {
        return itService.getHostsProjections();
    }

    @GetMapping("/getAssignmentId")
    public ResponseEntity<Map<String, String>> getAssignmentId() {
        return ResponseEntity.ok(Collections.singletonMap("assignmentid", itService.getAssignmentId()));
    }

    @GetMapping("/getLeaseId")
    public ResponseEntity<Map<String, String>> getLeaseId() {
        return ResponseEntity.ok(Collections.singletonMap("leaseid", itService.generateLeaseId()));
    }

    @PostMapping("/assignHost")
    public ResponseEntity<Map<String, String>> assignHost(@RequestParam("assignmentid") String assignmentid,
                                                          @RequestParam("hostid") int hostid,
                                                          @RequestParam("employeeId") String employeeId,
                                                          @RequestParam("assignedDate") LocalDate assignedDate,
                                                          @RequestParam("remarks") String remarks) {
        return itService.assignHost(assignmentid, hostid, employeeId, remarks, assignedDate);
    }

    @PostMapping("/returnHost")
    public ResponseEntity<Map<String, String>> returnHost(@RequestParam("assignmentid") String assignmentid,
                                                          @RequestParam("hostid") int hostid,
                                                          @RequestParam("returnDate") LocalDate returnDate,
                                                          @RequestParam("returnRemarks") String returnRemarks) {
        return itService.returnHost(assignmentid, hostid, returnDate, returnRemarks);
    }

   @PostMapping("/addSubscription")
    public ResponseEntity<Map<String, String>> addSubscription(
            @RequestParam("sname") String sname,
            @RequestParam("type") String type,
            @RequestParam("provider") String provider,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("expiryDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expiryDate,
            @RequestParam(value = "invoiceRefNo",required = false) String invoiceRefNo,
            @RequestParam(value = "invoiceFile", required = false) MultipartFile invoiceFile) {

        Map<String, String> response = new HashMap<>();
        String savedFilePath = null;

        if (invoiceFile != null && !invoiceFile.isEmpty()) {
            try {
            String projectPath = System.getProperty("user.dir");

            String uploadDirectory = projectPath + "/src/main/resources/static/IT/subscriptions/";

                Path uploadPath = Paths.get(uploadDirectory);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = invoiceFile.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }

                String safeFilename = UUID.randomUUID() + fileExtension;
                Path targetLocation = uploadPath.resolve(safeFilename);

                // Writes using a stream rather than loading the whole byte array into memory
                Files.copy(invoiceFile.getInputStream(), targetLocation);

                // Relative URI or persistent reference to store in DB
                savedFilePath = "/IT/subscriptions/" + safeFilename;

            } catch (IOException e) {
                response.put("status", "error");
                response.put("message", "File upload failed: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

        return itService.addSubcriptionToServerService(
                sname, type, provider, phone, email, startDate, expiryDate, savedFilePath,invoiceRefNo
        );
    }
    @PostMapping("/renewSubscription")
    public ResponseEntity<Map<String, String>> renewSubscription(
            @RequestParam("id") int id,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("expiryDate") LocalDate expiryDate,
            @RequestParam(value = "refId",required = false) String refId,
            @RequestParam(value = "invoiceFile",required = false) MultipartFile invoiceFile
    ) {
              Map<String, String> response = new HashMap<>();
        String savedFilePath = null;

        if (invoiceFile != null && !invoiceFile.isEmpty()) {
            try {
            String projectPath = System.getProperty("user.dir");

            String uploadDirectory = projectPath + "/src/main/resources/static/IT/subscriptions/";

                Path uploadPath = Paths.get(uploadDirectory);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = invoiceFile.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }

                String safeFilename = UUID.randomUUID() + fileExtension;
                Path targetLocation = uploadPath.resolve(safeFilename);

                // Writes using a stream rather than loading the whole byte array into memory
                Files.copy(invoiceFile.getInputStream(), targetLocation);

                // Relative URI or persistent reference to store in DB
                savedFilePath = "/IT/subscriptions/" + safeFilename;

            } catch (IOException e) {
                response.put("status", "error");
                response.put("message", "File upload failed: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
        
        return itService.renewSubscriptionService(id, startDate, expiryDate, refId, savedFilePath);
    }
    

    @GetMapping("/getSubscriptions")
    public List<SubscriptionsProjection> getSubscriptions() {
        return itService.getSubscriptionsDto();
    }
}