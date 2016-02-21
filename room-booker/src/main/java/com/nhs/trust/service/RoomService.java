package com.nhs.trust.service;
/*
 * Created by arif.mohammed on 30/10/2015.
 */

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.nhs.trust.common.TewvServiceException;
import com.nhs.trust.domain.Room;

public interface RoomService extends CommonService<Room> {
    List<Room> findByBuilding(String buildingName);
    List<Room> findByUser(String user);
    Map<String, Set<String>> findSiteAndBuildings() throws TewvServiceException;
    List<Room> addBatch(Set<Room> roomEntities);
    Room findByNameAndBuilding(String buildingName, String roomName);
	Room addAndUpdateRelatedBookingData(String addRoomJson) throws TewvServiceException;
	String uploadRoomFile(MultipartFile file) throws TewvServiceException;
}
