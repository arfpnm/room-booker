package com.nhs.trust.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nhs.trust.common.TewvServiceException;
import com.nhs.trust.domain.Building;
import com.nhs.trust.domain.Equipment;
import com.nhs.trust.domain.InactiveRoomDetails;
import com.nhs.trust.domain.Room;
import com.nhs.trust.domain.RoomBooking;
import com.nhs.trust.domain.RoomEquipment;
import com.nhs.trust.domain.Site;
import com.nhs.trust.repository.BuildingRepository;
import com.nhs.trust.repository.EquipmentRepository;
import com.nhs.trust.repository.RoomRepository;
import com.nhs.trust.service.RoomBookingService.Status;
import com.nhs.trust.utils.LoadCsvUtil;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

	private static Logger logger = LoggerFactory.getLogger(CommonService.class);

	@Autowired
	RoomRepository roomRepository;
	@Autowired
	private RoomBookingService roomBookingService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private RoomEquipmentService roomEquipmentService;

	@Autowired
	private LoadCsvUtil csvUtil;

	@Override
	public Room add(Room room) throws TewvServiceException {
		try {
			return roomRepository.save(room);
		} catch (Exception e) {
			if(e.getMessage().contains("org.hibernate.exception.ConstraintViolationException")){
				//String message = "Room '"+room.getRoomName()+"' with capacity of "+room.getCapacity()+", already exists for site '"+room.getSite().getSite()+"' : "+e.getMessage();
				String message = "Room '"+room.getRoomName()+"' with capacity of "+room.getCapacity()+", already exists for building '"+room.getBuilding().getBuildingName()+"' : "+e.getMessage();
				logger.error(message);
				throw new TewvServiceException(message);
			}
			throw new TewvServiceException("Service Exception while insert/update:"+e.getMessage());
		}
	}

	@Override
	public List<Room> addBatch(Set<Room> roomEntities) {
		return roomRepository.save(roomEntities);
	}

	@Override
	public List<Room> findAll() {
		return roomRepository.findAll();
	}

	@Override
	public Room findById(long id) {
		return roomRepository.findOne(id);
	}

	@Override
	public List<Room> findByBuilding(String buildingName) {
		return roomRepository.findByBuilding(buildingName);
	}

	@Override
	public List<Room> findByUser(String user) {
		List<Room> matchingUsers=new ArrayList<Room>();
		List<Room> roomList = roomRepository.findAll();
		for(Room room : roomList){
			String existingUser = room.getIndividualOwner();
			if(existingUser != null) {
				if(existingUser.contains("/")){
					for (String usr : existingUser.split("/")){
						if(usr.equalsIgnoreCase(user)){
							matchingUsers.add(room);
						}
					}
				}
				else{
					if(existingUser.equalsIgnoreCase(user)){
						matchingUsers.add(room);
					}
				}
			}
		}
		return matchingUsers;
	}

	@Value("${delim}")
	private String delim;

//	@Override
//	public Map<String, Set<String>> findSiteAndBuildings() throws TewvServiceException {
//		Map<String, Set<String>> locationSiteMap=null;
//		try {
//			List<Room> roomList = roomRepository.findAll();
//			locationSiteMap = new TreeMap<String, Set<String>>();
//			Set<String> locationBuildings;
//			String siteNBuilding;
//			for(Room room : roomList){
//				locationBuildings = new TreeSet<String>();
//				Site site = room.getBuilding().getSite();
//				if(site != null && site.getSite() != null){
//					siteNBuilding=
//							//site.getSite().equalsIgnoreCase(room.getBuilding()) ? site.getSite() : site.getSite()+delim+room.getBuilding();
//							site.getSite().equalsIgnoreCase(site.getBuilding()) ? site.getSite() : site.getSite()+delim+site.getBuilding();
//							locationBuildings.add(siteNBuilding);
//							if(locationSiteMap.containsKey(site.getTown())){
//								locationBuildings=locationSiteMap.get(site.getTown());
//								locationBuildings.add(siteNBuilding);
//							}
//							locationSiteMap.put(site.getTown(), locationBuildings);
//				}
//			}
//		} catch (Exception e) {
//			throw new TewvServiceException(e);
//		}
//		return locationSiteMap;
//	}
	
	
	@Override
	public Map<String, Set<String>> findSiteAndBuildings() throws TewvServiceException {
		Map<String, Set<String>> locationSiteMap=null;
		try {
			List<Room> roomList = roomRepository.findAll();
			locationSiteMap = new TreeMap<String, Set<String>>();
			Set<String> locationBuildings;
			String siteNBuilding;
			for(Room room : roomList){
				locationBuildings = new TreeSet<String>();
				Building building = room.getBuilding();
				if(building != null && building.getSite() != null){
					siteNBuilding=
							//site.getSite().equalsIgnoreCase(room.getBuilding()) ? site.getSite() : site.getSite()+delim+room.getBuilding();
							building.getSite().getSiteName().equalsIgnoreCase(building.getBuildingName()) ? building.getBuildingName() : building.getSite().getSiteName()+delim+building.getBuildingName();
							locationBuildings.add(siteNBuilding);
							if(locationSiteMap.containsKey(building.getSite().getTown())){
								locationBuildings=locationSiteMap.get(building.getSite().getTown());
								locationBuildings.add(siteNBuilding);
								
							}
							locationSiteMap.put(building.getSite().getTown(), locationBuildings);
				}
			}
		} catch (Exception e) {
			throw new TewvServiceException(e);
		}
		return locationSiteMap;
	}

	public Room findByNameAndBuilding(String buildingName, String roomName){
		return roomRepository.findByNameAndBuilding(buildingName, roomName);
	}

	@Override
	public Room addAndUpdateRelatedBookingData(String addRoomJson) throws TewvServiceException{

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject jsonObject = new JSONObject(addRoomJson);
		JSONArray equipmentArray = jsonObject.getJSONArray("equipment");
		Room room=null;
		long buildingId = jsonObject.getLong("buildingId");
		try {
			room = new ObjectMapper().readValue(addRoomJson, Room.class);
		} catch (JsonParseException e) {
			throw new TewvServiceException(e);
		} catch (JsonMappingException e) {
			throw new TewvServiceException(e);
		} catch (IOException e) {
			throw new TewvServiceException(e);
		}
		
		Building building = buildingRepository.findOne(buildingId);
		room.setBuilding(building);

		if(room.getRoomId() != 0){
			roomEquipmentService.deleteByRoomId(room.getRoomId());
		}
		
		Set<RoomEquipment> roomEquipmentList = new HashSet<RoomEquipment>();
		RoomEquipment roomEquipment=null;
		for(Object o : equipmentArray){
			JSONObject jsonLineItem = (JSONObject) o;
			long equipmentId = jsonLineItem.getLong("id");
			roomEquipment = new RoomEquipment();
			Equipment equipment = equipmentRepository.findOne(equipmentId);		
			roomEquipment.setRoomEquipment(equipment);
			roomEquipment.setRoom(room);
			roomEquipmentList.add(roomEquipment);
		}

		room.setEquipment(roomEquipmentList);
		/**
		room.setSite( (room.getSite() == null || room.getSite().getSite() == null ) && siteId != 0 ? 
				siteService.findById(siteId) : room.getSite());
		room.setSiteName( (room.getSiteName() == null || room.getSiteName().isEmpty()) &&  room.getSite() != null ? 
				room.getSite().getSite() : room.getSiteName());
		**/


		Set<RoomBooking> allMatchingBookings = new HashSet<RoomBooking>();
		if(room.getInactiveRooms() != null){
			for(InactiveRoomDetails inactiveRoomDetails : room.getInactiveRooms()){
				if(inactiveRoomDetails.getStatus().equalsIgnoreCase(Status.ACTIVE.getCurrentStatus())){
					if(inactiveRoomDetails.getCancelExistingBooking().equalsIgnoreCase("Y")){
						allMatchingBookings = 
								roomBookingService.setStatusForRoomAndDateRange
								(room.getRoomId(), inactiveRoomDetails.getStartDate(),inactiveRoomDetails.getEndDate(), Status.NOSERIVICE.getCurrentStatus());
					}
				}
				else if(inactiveRoomDetails.getStatus().equalsIgnoreCase(Status.INACTIVE.getCurrentStatus())){
					allMatchingBookings = 
							roomBookingService.setStatusForRoomAndDateRange
							(room.getRoomId(), inactiveRoomDetails.getStartDate(),inactiveRoomDetails.getEndDate(), null);
				}
			}
		}
			if(allMatchingBookings != null && allMatchingBookings.size() > 0){
				roomBookingService.addBatch(allMatchingBookings);
			}
			return add(room);
	}
	
	@Override
	public String uploadRoomFile(MultipartFile file) throws TewvServiceException{

		//Set<Room> roomList=null;
		Set<Room> csvList;
		try {
			csvUtil.insertBuildingCsvInputList(file.getInputStream());
			csvList = csvUtil.getRoomCsvInputList(file.getInputStream());
		} catch (IOException e) {
			throw new TewvServiceException(e);
		} catch (Exception e) {
			throw new TewvServiceException(e);
		}
//		try {
//			Set<Room> csvList = csvUtil.getRoomCsvInputList(file.getInputStream());
//			roomList = new HashSet<Room>();
//			Room existingRecord=null;
//			for (Room inputRecord : csvList) {
//				
//				roomList.add(inputRecord);
//			}
//		} catch (IOException e) {
//			throw new TewvServiceException(e);
//		} catch (Exception e) {
//			throw new TewvServiceException(e);
//		}
		if(csvList != null && csvList.size() > 0){
			addBatch(csvList);
		}
		return "success";
	}
	
	

/*	@Override
	public String uploadRoomFile(MultipartFile file) throws TewvServiceException{

		Set<Room> roomList=null;
		try {
			Set<Room> csvList = csvUtil.getRoomCsvInputList(file.getInputStream());
			roomList = new HashSet<Room>();
			Room existingRecord=null;
			for (Room inputRecord : csvList) {
				existingRecord=findByNameAndBuilding(inputRecord.getBuilding(), inputRecord.getRoomName());
				if(existingRecord == null){
					Site site = siteService.findByName(inputRecord.getSiteName());
					site.setBuilding(inputRecord.getBuilding());
					inputRecord.setSite(site);
				}
				else{
					inputRecord.setRoomId(existingRecord.getRoomId());
					if(existingRecord.getSite() == null){
						Site site = siteService.findByName(inputRecord.getSiteName());
						site.setBuilding(inputRecord.getBuilding());
						inputRecord.setSite(site);
					}
					else{
						existingRecord.getSite().setBuilding(inputRecord.getBuilding());
						inputRecord.setSite(existingRecord.getSite());
					}
				}
				roomList.add(inputRecord);
			}
		} catch (IOException e) {
			throw new TewvServiceException(e);
		} catch (Exception e) {
			throw new TewvServiceException(e);
		}
		if(roomList != null && roomList.size() > 0){
			addBatch(roomList);
		}
		return "success";
	}*/
}
