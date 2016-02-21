package com.nhs.trust.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhs.trust.domain.Building;
import com.nhs.trust.domain.Equipment;

public interface BuildingRepository extends JpaRepository< Building, Long>, JpaSpecificationExecutor<Equipment>{

	@Query(value = "SELECT building FROM Building building WHERE site_id = :siteId")
	List<Building> findBySite(@Param("siteId") Long siteId);
	
	@Query(value = "SELECT building FROM Building building WHERE buildingName = :buildingName")
	Building findByName(@Param("buildingName") String buildingName);
}

