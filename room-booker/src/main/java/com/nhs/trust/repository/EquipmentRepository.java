package com.nhs.trust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhs.trust.domain.Equipment;


public interface EquipmentRepository extends JpaRepository< Equipment, Long>, JpaSpecificationExecutor<Equipment>{
	
	@Query(value = "SELECT equipment FROM Equipment equipment WHERE equipment.equipment = :equipmentName")
	Equipment findByName(@Param("equipmentName") String equipmentName);

}
