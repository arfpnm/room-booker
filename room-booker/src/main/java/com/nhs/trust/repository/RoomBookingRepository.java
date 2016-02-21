package com.nhs.trust.repository;

/*
 * Created by arif.mohammed on 30/10/2015.
 */
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhs.trust.domain.RoomBooking;

//@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long>, JpaSpecificationExecutor<RoomBooking>{

	//Changed to point site.building
	//@Query(value = "SELECT roomBooking FROM RoomBooking roomBooking WHERE roomBooking.room.site.building = :buildingName")
	@Query(value = "SELECT roomBooking FROM RoomBooking roomBooking WHERE roomBooking.room.building.buildingName = :buildingName")
	List<RoomBooking> findByBuilding(@Param("buildingName") String buildingName);

	@Query(value = "SELECT roomBooking FROM RoomBooking roomBooking WHERE roomBooking.room.roomId = :roomId")
	List<RoomBooking> findByRoom(@Param("roomId") long roomId);

	@Query(value = "SELECT roomBooking FROM RoomBooking roomBooking WHERE roomBooking.room.roomId = :roomId AND "
			+ "roomBooking.startTime >= :fromDate AND roomBooking.endTime <= :toDate")
	List<RoomBooking> findByRoomWithDateRange(@Param("roomId") Long roomId, @Param("fromDate") Date startDate, 
			@Param("toDate") Date endDate);

//	@Query(value = "SELECT roomBooking FROM RoomBooking roomBooking WHERE roomBooking.room.site.town= :area "
//			+ "AND roomBooking.room.site.site = :location AND roomBooking.room.roomId= :roomId AND "
//			+ "roomBooking.title= :title AND roomBooking.startTime >= :fromDate AND roomBooking.endTime <= :toDate")
	@Query(value = "SELECT roomBooking FROM RoomBooking roomBooking WHERE roomBooking.room.building.site.town= :area "
			+ "AND roomBooking.room.building.site.siteName = :location AND roomBooking.room.roomId= :roomId AND "
			+ "roomBooking.title= :title AND roomBooking.startTime >= :fromDate AND roomBooking.endTime <= :toDate")
	List<RoomBooking> searchCriteriaForReporting(@Param("area") String area, @Param("location") String location, 
			@Param("roomId") Long roomId, @Param("title") String title, @Param("fromDate") Date startDate, 
			@Param("toDate") Date endDate);
}
