package com.nhs.trust.service;
/*
 * Created by arif.mohammed on 30/10/2015.
 */

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhs.trust.common.TewvServiceException;
import com.nhs.trust.domain.RoomBooking;
import com.nhs.trust.repository.RoomBookingRepository;

@Service
@Transactional
public class RoomBookingServiceImpl implements RoomBookingService {

    private static Logger logger = LoggerFactory.getLogger(RoomBookingServiceImpl.class);

    @Autowired
    private RoomBookingRepository roomBookingRepository;

	@Override
	public RoomBooking add(RoomBooking roomBooking) throws TewvServiceException {
		try {
			return roomBookingRepository.save(roomBooking);
		} catch (Exception e) {
			throw new TewvServiceException("Service exception : "+e.getMessage());
		}
	}
    
    @Override
    public List<RoomBooking> addBatch(Set<RoomBooking> roomBookingEntities) {
    	return roomBookingRepository.save(roomBookingEntities);
    }

    @Override
    public RoomBooking cancel(Long bookingId) {
    	RoomBooking roomBooking = roomBookingRepository.findOne(bookingId);
    	roomBooking.setStatus(Status.CANCELLED.currentStatus);
    	roomBooking.setCancelDate(new Date());
    	return roomBookingRepository.save(roomBooking);
    }

    @Override
    public List<RoomBooking> findAll() {
        return roomBookingRepository.findAll();
    }

    @Override
    public RoomBooking findById(long id) {
        return roomBookingRepository.findOne(id);
    }

    @Override
    public List<RoomBooking> findByBuilding(String buildingName) {
        return roomBookingRepository.findByBuilding(buildingName);
    }

    @Override
    public List<RoomBooking> findByRoom(String roomId) {
        return roomBookingRepository.findByRoom(Long.valueOf(roomId));
    }

    @Override
    public List<RoomBooking> findByBuildingWithDateRange(String buildingName, Date fromDate, Date toDate) {
      	return roomBookingRepository.findAll(RoomBooking.getBuildingsForDateRange(buildingName, fromDate, toDate));
    }
    
    @Override
    public List<RoomBooking> findByRoomWithDateRange(Long roomId, Date fromDate, Date toDate) {
    	List<RoomBooking> roomBookingList = roomBookingRepository.findByRoomWithDateRange(roomId, fromDate, toDate);
        return roomBookingList;
    }
    
    @Override
    public List<RoomBooking> searchRoomBookingForReporting(String town, String site, Long roomId, String title, Date fromDate, Date toDate){
    	return roomBookingRepository.findAll(RoomBooking.getSearchCreteriaForReport(town, site, roomId, title, fromDate, toDate));
    }
    
    @Override
	public Set<RoomBooking> setStatusForRoomAndDateRange(long roomId, Date startDate, Date endDate, String status){
		List<RoomBooking> bookings=findByRoomWithDateRange(roomId, startDate, endDate);
		bookings.stream().forEach(rb -> rb.setStatus(status));
		return new HashSet<RoomBooking>(bookings);
	}

}
