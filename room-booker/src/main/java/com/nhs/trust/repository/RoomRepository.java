package com.nhs.trust.repository;
/*
 * Created by arif.mohammed on 30/10/2015.
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhs.trust.domain.Room;

//@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
//Changes
	//@Query(value = "SELECT room FROM Room room WHERE room.site.building = :buildingName")
	@Query(value = "SELECT room FROM Room room WHERE room.building.buildingName = :buildingName")
	List<Room> findByBuilding(@Param("buildingName") String buildingName);
	
	//Changed from room.building to room.site.building
	//@Query(value = "SELECT room FROM Room room WHERE room.site.building = :buildingName and room.roomName = :roomName")
	@Query(value = "SELECT room FROM Room room WHERE room.building.buildingName = :buildingName and room.roomName = :roomName")
	Room findByNameAndBuilding(@Param("buildingName") String buildingName, @Param("roomName") String roomName);

	//@Query(value = "SELECT room FROM Room room group by room.building order by room.siteName")
	//List<Room> findTownAndBuildings();


}
