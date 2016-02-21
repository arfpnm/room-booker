package com.nhs.trust.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhs.trust.domain.Equipment;
import com.nhs.trust.domain.RoomEquipment;


public interface RoomEquipmentRepository extends JpaRepository< RoomEquipment, Long>, JpaSpecificationExecutor<Equipment>{
	
	@Query(value = "SELECT r_equipment FROM RoomEquipment r_equipment WHERE roomId = :roomId")
	List<RoomEquipment> findByRoom(@Param("roomId") Long roomId);
	
	@Query(value = "DELETE FROM RoomEquipment r_equipment WHERE room.roomId = :roomId")
	@Modifying
	void deleteByRoomId(@Param("roomId") Long roomId);

}
