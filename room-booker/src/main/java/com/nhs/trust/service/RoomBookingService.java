package com.nhs.trust.service;
/*
 * Created by arif.mohammed on 30/10/2015.
 */

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.nhs.trust.domain.RoomBooking;

public interface RoomBookingService extends CommonService<RoomBooking> {

	List<RoomBooking> findByBuilding(String buildingName);

	List<RoomBooking> findByRoom(String roomId);

	List<RoomBooking> findByBuildingWithDateRange(String buildingName, Date fromDate, Date toDate);

	List<RoomBooking> findByRoomWithDateRange(Long roomId, Date fromDate, Date toDate);

	RoomBooking cancel(Long bookingId);

	List<RoomBooking> searchRoomBookingForReporting(String town, String site, Long roomId, String title, Date fromDate, Date toDate);
	
	List<RoomBooking> addBatch(Set<RoomBooking> roomBookingEntities);

	Set<RoomBooking> setStatusForRoomAndDateRange(long roomId, Date startDate,
			Date endDate, String status);

	enum Status {

		PENDING("Pending Approval"),
		APPROVED("Approved"),
		REJECTED("Rejected"),
		CANCELLED("Cancelled"),
		NOSERIVICE("Out Of Service"),
		ACTIVE("ACTIVE"),
		INACTIVE("INACTIVE");

		Status(String currentStatus){
			this.currentStatus=currentStatus;
		}

		String currentStatus;

		public String getCurrentStatus() {
			return currentStatus;
		}

	}
}
