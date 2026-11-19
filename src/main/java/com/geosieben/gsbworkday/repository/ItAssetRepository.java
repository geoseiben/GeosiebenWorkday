package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.AssetResponseDto;
import com.geosieben.gsbworkday.entity.ItAsset;
public interface ItAssetRepository extends JpaRepository<ItAsset,Integer>{

    @Query(value="SELECT * FROM itasset where serialno=:serialNo",nativeQuery = true)
     public ItAsset findBySerialNo(@Param("serialNo") String serialNo);

     @Query(value = "select assetid,assetName,serialNo from itasset where category=:category",nativeQuery = true)
     public List<AssetResponseDto> getAssetsByCatgeory(@Param("category") String category);
          @Query(value = "select assetid,assetName,serialNo from itasset where category=:category and assetid<>:assetid ",nativeQuery = true)
     public List<AssetResponseDto> getAssetsByCatgeoryExceptId(@Param("category") String category,@Param("assetid") int assetid);
}
