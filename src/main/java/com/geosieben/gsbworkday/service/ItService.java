package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.repository.AssetAssignmentRepository;
import com.geosieben.gsbworkday.repository.AssetLeaseRepository;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;

import com.geosieben.gsbworkday.repository.HostsRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.geosieben.gsbworkday.dto.AssetResponseDto;
import com.geosieben.gsbworkday.dto.HostRequestDto;
import com.geosieben.gsbworkday.dto.HostsProjection;
import com.geosieben.gsbworkday.dto.SubscriptionsProjection;
import com.geosieben.gsbworkday.entity.AssetAssignment;
import com.geosieben.gsbworkday.entity.AssetLease;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.Hosts;
import com.geosieben.gsbworkday.entity.ItAsset;
import com.geosieben.gsbworkday.entity.SubscriptionHistory;
import com.geosieben.gsbworkday.entity.Subscriptions;
import com.geosieben.gsbworkday.repository.ItAssetRepository;
import com.geosieben.gsbworkday.repository.SubscriptinHistoryRepository;
import com.geosieben.gsbworkday.repository.SubscriptionRepository;
import com.geosieben.gsbworkday.repository.VendorRepository;

import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
@Service
public class ItService implements ItServiceInterface {

private final HostsRepository hostsRepository;
private final BasicInfoRepository basicInfoRepository;
private final VendorRepository vendorRepository;
private ItAssetRepository itAssetRepository;
private AssetAssignmentRepository assetAssignmentRepository;
private HttpSession httpSession;
private AssetLeaseRepository assetLeaseRepository;
private final SubscriptionRepository subscriptionRepository;
private final SubscriptinHistoryRepository subscriptinHistoryRepository;
//Constructor to initialize the BasicInfoRepository

    @Override
    public ResponseEntity validateSerialNo(String serialNo) {
        //Finding an asset by its serial number using the repository
        ItAsset asset=(ItAsset)itAssetRepository.findBySerialNo(serialNo);
        Map<Object, Object> response=new HashMap<>();
        if(asset!=null) {
            response.put("status", "success");
                response.put("assetid", asset.getAssetid());
        }
        else {
            response.put("status", "success");
                            response.put("assetid", null);

            
        }   
        return ResponseEntity.ok(response);
    }

    public ItService(BasicInfoRepository basicInfoRepository, ItAssetRepository itAssetRepository,
        AssetAssignmentRepository assetAssignmentRepository, HttpSession httpSession,
         HostsRepository hostsRepository,AssetLeaseRepository assetLeaseRepository,SubscriptionRepository subscriptionRepository,
         SubscriptinHistoryRepository subscriptinHistoryRepository,VendorRepository vendorRepository) {
        this.basicInfoRepository = basicInfoRepository;
        this.itAssetRepository = itAssetRepository;
        this.assetAssignmentRepository = assetAssignmentRepository;
        this.httpSession = httpSession;
        this.hostsRepository = hostsRepository;
        this.assetLeaseRepository=assetLeaseRepository;
        this.subscriptionRepository=subscriptionRepository;
        this.subscriptinHistoryRepository=subscriptinHistoryRepository;
        this.vendorRepository=vendorRepository;
    }

    @Override
    public ResponseEntity<?> addAsset(String assetName, String category, String model, String serialNo,
            Integer vendor,String brand,String type,String batchId) {
     //Creating a new ItAsset object and setting its properties based on the input parameters
      ItAsset asset=new ItAsset();
        asset.setAssetName(assetName);
        asset.setBrand(brand);
        asset.setBatchId(batchId);
        asset.setType(type);
        asset.setCategory(category);
        asset.setModel(model);
        asset.setSerialNo(serialNo);
        asset.setVendor(vendorRepository.findById(Integer.valueOf(vendor)).orElse(null));
        asset.setCreatedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
        itAssetRepository.save(asset);
        return ResponseEntity.ok(Map.of("message", "Asset added successfully", "status", "success"));
    }
    @Override
    public List<ItAsset> getAllAssets() {
        return itAssetRepository.findAll();
    }
    @Transactional
    @Override
    public ResponseEntity<?> updateAsset(int assetId, String assetName, String category, String model, String serialNo,
            String vendor) {
                Optional<ItAsset> optionalAsset=itAssetRepository.findById(assetId);
                if(optionalAsset.isPresent()){
                    ItAsset asset=optionalAsset.get();
                    asset.setAssetName(assetName);
                    asset.setCategory(category);
                    asset.setModel(model);
                    asset.setSerialNo(serialNo);
                    asset.setVendor(vendorRepository.findById(Integer.valueOf(vendor)).orElse(null));
                    itAssetRepository.save(asset);
                    return ResponseEntity.ok(Map.of("message", "Asset updated successfully", "status", "success"));
                }
                else {
                    return ResponseEntity.status(404).body(Map.of("message", "Asset not found", "status", "error"));
                }
                
    }
    @Override
    public List<EmployeeBasicInfo> getMembers() {
       return basicInfoRepository.findAll();
    }
    @Override
    @Transactional
    public ResponseEntity<Map<String, String>> assignAssset(int assetid, String eid, String remarks) {
            String assignmentid=generateAssignmentid();
            AssetAssignment assignment=new AssetAssignment();
            assignment.setAssignedTo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
            assignment.setAssignedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));
            assignment.setAssignmentId(assignmentid);
            assignment.setAssignmentRemarks(remarks);
            assignment.setLaptop(itAssetRepository.findById(assetid).orElse(null));
            assetAssignmentRepository.save(assignment);

            Optional<ItAsset> assset=itAssetRepository.findById(assetid);
            if(assset.isPresent()){
                ItAsset a=assset.get();
                a.setStatus(1);
                a.setAvailable(0);
                a.setAssignedTo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
            }
            
            Map<String,String> response=new HashMap<>();
            
            response.put("status", "success");
            response.put("message","Asset Assigned SuccessFully");
            return ResponseEntity.ok(response);

    }

    public String generateAssignmentid(){
           String assignmentid;
        Optional<AssetAssignment> max=assetAssignmentRepository.findMaxAssignmentid();
        if(max.isEmpty()){
         assignmentid="IT"+(LocalDate.now().toString().substring(0,4))+"00001";
        }
        else{
            String maxid=max.get().getAssignmentId();
            String year=maxid.substring(2,6);
            if(LocalDate.now().toString().substring(0,4).equals(year)){
                int numPart=Integer.parseInt(maxid.substring(2,maxid.length()));
                assignmentid="IT"+(++numPart);
            }
            else{
                 assignmentid="IT"+(LocalDate.now().toString().substring(0,4))+"00001";
        }
            }
            
        return assignmentid;
        }
    @Override
    public List<AssetResponseDto> getAssets(String asset,int dependencyValue) {
            Map<String,String> assetCatMap=new HashMap<>();
            assetCatMap.put("Monitor1", "Monitors");
            assetCatMap.put("Monitor2", "Monitors");
            assetCatMap.put("mouse", "Mouse");
            assetCatMap.put("keyboard", "Keyboards");
            assetCatMap.put("cabinet", "Cabinets");
            assetCatMap.put("ssd", "HDDs / SSDs");
            assetCatMap.put("hdd", "HDDs / SSDs");
            assetCatMap.put("goggle", "Goggles");
            assetCatMap.put("emitter", "Emitter");
            assetCatMap.put("ups", "Accessories");
            assetCatMap.put("powercable", "Power Cables");
            assetCatMap.put("videocable", "Video Cables");
            assetCatMap.put("headphones", "Headphones");
            assetCatMap.put("accessories", "Accessories");
            assetCatMap.put("emittercharger", "Accessories");



            if(dependencyValue==0){
            return itAssetRepository.getAssetsByCatgeory(assetCatMap.get(asset));
            }
            
                return itAssetRepository.getAssetsByCatgeoryExceptId(assetCatMap.get(asset), dependencyValue);
    }



public ResponseEntity<Map<String, String>> addHost(HostRequestDto dto) {
    Map<String, String> response = new HashMap<>();
    try {
        Hosts host = new Hosts();

        // 1. Basic Fields Mapping
        host.setHostType(dto.getHostType());
        host.setHostname(dto.getHostName());
        host.setLanNo(dto.getLanno());
        host.setProcessor(dto.getProcessor());
        host.setRam(dto.getRam());
        host.setGraphics(dto.getGraphics());
        host.setOs(dto.getOs());
        host.setIpAddress(dto.getIpAddress());
        host.setIsAvailable(1); // Default value as per entity
        host.setStatus(0);       // Default status

        // 2. Map IT Asset Dependencies using Helper
        host.setCabinet(dto.getCabinet());
        host.setAd1(dto.getAd1());
        host.setAd2(dto.getAd2());
        host.setEmmitter(fetchItAsset(dto.getEmitter()));
        host.setGoggle(fetchItAsset(dto.getGoggle()));
        host.setMonitor1(fetchItAsset(dto.getMonitor1()));
        host.setMon1vc(dto.getMonitor1_vcable());
        host.setMonitor2(fetchItAsset(dto.getMonitor2()));
        host.setMon2vc(dto.getMonitor2_vcable());
        host.setSsd(dto.getSsd());
        host.setHdd((dto.getHdd()));
        host.setHeadphone(fetchItAsset(dto.getHeadphones()));
        host.setUps(fetchItAsset(dto.getUps()));
        host.setMouse(fetchItAsset(dto.getMouse()));
        host.setKeyboard(fetchItAsset(dto.getKeyboard()));

        // 3. Map non-IT Asset Entity Relationships
      
            host.setCreatedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute(("eid"))));
        


        // 4. Save Host Entity
hostsRepository.save(host);
        // 5. Array Validation / Extra Logic (if needed for batch processing)
        String[] assetArr = {

            dto.getEmitter(),dto.getGoggle(), dto.getMonitor1(),
            dto.getHeadphones(), dto.getUps(), dto.getMouse(), dto.getKeyboard()
        };

        // Example: Update status for allocated IT Assets
        for (String assetIdStr : assetArr) {
            ItAsset asset = fetchItAsset(assetIdStr);
            if (asset != null) {
                asset.setStatus(1);
                asset.setIsAvailable(0);
                itAssetRepository.save(asset);
            }
        }

        response.put("status", "success");
        response.put("message", "Host added successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    } catch (NumberFormatException e) {
        response.put("status", "error");
        response.put("message", "Invalid asset or ID format");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        response.put("status", "error");
        response.put("message", "An unexpected error occurred: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

/**
 * Utility helper to safely parse IDs and retrieve ItAsset entities.
 */
private ItAsset fetchItAsset(String assetId) {
    if (assetId == null || assetId.trim().isEmpty()) {
        return null;
    }
    return itAssetRepository.findById(Integer.valueOf(assetId.trim())).orElse(null);
}

public List<HostsProjection> getHostsProjections(){
    return hostsRepository.getAllHosts();
}
public String getAssignmentId(){
    Optional<AssetAssignment> assignment=assetAssignmentRepository.findMaxAssignmentid();
            String newAssignmentid=null;
        int currentYear=(LocalDate.now().getYear());

    if(assignment.isPresent()){
        String assignmentid=assignment.get().getAssignmentId();
        String year=assignmentid.substring(2,6);
        if(String.valueOf(currentYear).equals(year)){
            int intpart=Integer.parseInt(assignmentid.substring(2,assignmentid.length()));
            newAssignmentid="IT"+(intpart+1);

        }else{
        newAssignmentid="IT"+currentYear+"00001";

        }
    }
    else{
        newAssignmentid="IT"+currentYear+"00001";

    }
    return newAssignmentid;
}

public String generateLeaseId(){
    Optional<AssetLease> lease=assetLeaseRepository.getMaxAssignmentId();
            String newAssignmentid=null;
        int currentYear=(LocalDate.now().getYear());

    if(lease.isPresent()){
        String assignmentid=lease.get().getLeaseId();
        String year=assignmentid.substring(4,12);
        if(String.valueOf(currentYear).equals(year)){
            int intpart=Integer.parseInt(assignmentid.substring(2,assignmentid.length()));
            newAssignmentid="ITLS"+(intpart+1);

        }else{
        newAssignmentid="ITLS"+currentYear+"00001";

        }
    }
    else{
        newAssignmentid="ITLS"+currentYear+"00001";

    }
    return newAssignmentid;
}
@Transactional
public ResponseEntity<Map<String, String>> assignHost(String assignmentid,int hostid,String employeeid,String remarks,LocalDate assignedDate){
            Map<String, String> response = new HashMap<>();
           assetAssignmentRepository.assignHost(assignmentid, hostid, assignedDate, (String) httpSession.getAttribute("eid"),employeeid);
            hostsRepository.hostAssignmentUpdate(1, assignmentid, hostid);
        response.put("status", "success");
        response.put("message", "Host Assigned successfully");

    return  ResponseEntity.ok(response);
}
@Transactional 
public  ResponseEntity<Map<String, String>> returnHost(String assignmentid,int hostid,LocalDate returnDate,String returnRemarks){
        Map<String, String> response = new HashMap<>();

    AssetAssignment assignment=assetAssignmentRepository.findById(assignmentid).orElse(null);
    if(assignment!=null){
        assignment.setReturnDate(returnDate);
        assignment.setReturnRemarks(returnRemarks);
        Hosts host=hostsRepository.findHostByAssignmentId(assignmentid).orElse(null);
        if(host !=null){
            host.setAssetAssignment(null);
            host.setStatus(0);
            hostsRepository.save(host);
        }
        assetAssignmentRepository.save(assignment);
                response.put("status", "success");
        response.put("message", "Host Returned successfully");
    }
    else{
        response.put("status", "error");
        response.put("message", "Error Returning Host");
    }
return ResponseEntity.ok(response);
}
@Transactional 
public  ResponseEntity<Map<String, String>> leaseHost(){
    return null;
}
@Transactional 
public  ResponseEntity<Map<String, String>> addSubcriptionToServerService(
    String sname,
    String type,
    String provider,
    String phone,
    String email,
    LocalDate startDate,
    LocalDate expiryDate,
    String filePath,
String refId){
        Map<String,String> response=new HashMap<>();
        try{

        Subscriptions subscription =new Subscriptions();
        subscription.setSubscriptionName(sname);
        subscription.setContactNumber(phone);
        subscription.setContactEmail(email);
        subscription.setSubscriptionProvider(provider);
        subscription.setSubscriptionType(type);

                Subscriptions savedSubscription =subscriptionRepository.save(subscription);
                  SubscriptionHistory history=new SubscriptionHistory();
                  history.setSubscription(savedSubscription);
                  history.setRenewalType(null);
                  history.setSubscriptionStartDate(startDate);
                  history.setSubscriptionExpiryDate(expiryDate);
                  history.setRenewedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));
                  history.setInvoicePath(filePath);
                  history.setRefId(refId);
                subscriptinHistoryRepository.save(history);
                response.put("status", "success");
                response.put("message", "Subscription Added");
        }catch(Exception e){
        response.put("status", "error");
        response.put("message", e.getMessage());
        }

        return  ResponseEntity.ok(response);
}
public  List<SubscriptionsProjection> getSubscriptionsDto(){
    return  subscriptionRepository.getSubscriptions();
}

public ResponseEntity<Map<String, String>> renewSubscriptionService(int subscriptionId,LocalDate startDate,LocalDate endDate,String refId,String filePath){
            Map<String,String> response=new HashMap<>();

    Subscriptions subscription=subscriptionRepository.findById(subscriptionId).orElse(null);
    SubscriptionHistory history=new SubscriptionHistory();
    history.setSubscription(subscription);
    history.setRefId(refId);
    history.setInvoicePath(filePath);
    history.setSubscriptionStartDate(startDate);
    history.setSubscriptionExpiryDate(endDate);
    history.setRenewedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));

    subscriptinHistoryRepository.save(history);
                    response.put("status", "success");
                response.put("message", "Subscription Added");
    return ResponseEntity.ok(response);

    }
}