package com.geosieben.gsbworkday.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.geosieben.gsbworkday.dto.AssetResponseDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import com.geosieben.gsbworkday.entity.ItAsset;

public interface ItServiceInterface {
    public ResponseEntity<Map<Object, Object>> validateSerialNo(String serialNo);
    public ResponseEntity<?> addAsset(String assetName,String category,String model,String serialNo,Integer vendor,String brand,String type,String batchId);
    public List<ItAsset> getAllAssets();
    public ResponseEntity<?> updateAsset(int assetId,String assetName,String category,String model,String serialNo,String vendor);
    public List<EmployeeBasicInfo> getMembers();
    public ResponseEntity<Map<String,String>> assignAssset(int assetid,String eid,String remarks);
    public List<AssetResponseDto> getAssets(String asset,int dependencyValue);

}
