package com.nhs.trust.utils;
/**
 * @author arif.mohammed
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.nhs.trust.domain.Building;
import com.nhs.trust.domain.Equipment;
import com.nhs.trust.domain.Room;
import com.nhs.trust.domain.RoomEquipment;
import com.nhs.trust.domain.Site;
import com.nhs.trust.repository.BuildingRepository;
import com.nhs.trust.repository.EquipmentRepository;
import com.nhs.trust.repository.SiteRepository;
import com.nhs.trust.service.BuildingService;

@Component
@CrossOrigin(origins={"http://10.100.8.177:18080","http://10.100.9.94:18080","http://localhost:8089"})
public class LoadCsvUtil {

	private static final String UTF8CHARSET = "UTF-8";

	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Autowired
	private SiteRepository siteRepository;
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private BuildingService buildingService;
	
	public Set<Building> insertBuildingCsvInputList(InputStream inputStream) throws Exception {
		Reader reader = null;
		Set<Building> csvBuildingList = new HashSet<Building>();
		Building buildingData;

		try {
			reader = new InputStreamReader(inputStream, UTF8CHARSET);
			Iterable<CSVRecord> records;
			records = CSVFormat.EXCEL.withHeader().parse(reader);

			for (CSVRecord record : records) {
				buildingData = new Building();
				buildingData.setSite(siteRepository.findByName(record.get("Site")));
				buildingData.setBuildingName(record.get("Building"));
				csvBuildingList.add(buildingData);

			}
			
			if(csvBuildingList != null && csvBuildingList.size() > 0){
				buildingService.addBatch(csvBuildingList);
			}
			
		}catch (Exception e) {
			throw e;
		}finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return csvBuildingList;
	}

	
	

	public Set<Room> getRoomCsvInputList(InputStream inputStream) throws Exception {
		Reader reader = null;
		Set<Room> csvList = new HashSet<Room>();
		Room roomCsvFile;

		try {
			reader = new InputStreamReader(inputStream, UTF8CHARSET);
			Iterable<CSVRecord> records;
			records = CSVFormat.EXCEL.withHeader().parse(reader);

			for (CSVRecord record : records) {
				roomCsvFile = new Room();
				roomCsvFile.setSiteName(record.get("Site"));
				String inputBuilding = record.get("Building");
				Building building = buildingRepository.findByName(inputBuilding);
				//Building building = new Building();
				if(building == null || building.getBuildingName() == null){
					building = new Building();
					building.setBuildingName(inputBuilding);
					building.setSite(siteRepository.findByName(roomCsvFile.getSiteName()));
				}
					roomCsvFile.setBuilding(building);		
				
				roomCsvFile.setRoomName(record.get("Room name"));
				roomCsvFile.setContact(record.get("Contact"));
				roomCsvFile.setTeamOwner(record.get("Owner - team"));
				roomCsvFile.setIndividualOwner(record.get("Owner- individual"));
				roomCsvFile.setRoomType(record.get("Room type"));
				roomCsvFile.setCategory(record.get("Category"));
				String equipmentStr = record.get("Equipment");
				equipmentStr=equipmentStr != null && !equipmentStr.isEmpty() ? equipmentStr=equipmentStr.replaceAll("\n", ",") : null;

				Set<RoomEquipment> roomEquipmentList = new HashSet<RoomEquipment>();
				if(equipmentStr != null && !equipmentStr.isEmpty()){
					String[] equipArray = equipmentStr.split(",");
					RoomEquipment roomEquipment = null;
					for(String equip : equipArray){
						roomEquipment = new RoomEquipment();
						Equipment equipment = equipmentRepository.findByName(equip);
						if(equipment != null && equipment.getEquipment() != null){
							roomEquipment.setRoomEquipment(equipment);
							roomEquipment.setRoom(roomCsvFile);
							roomEquipmentList.add(roomEquipment);
						}
					}
				}
				roomCsvFile.setEquipment(roomEquipmentList);				
				roomCsvFile.setCapacity(record.get("Capacity (Max)"));
				roomCsvFile.setCatering(record.get("Catering"));
				roomCsvFile.setAccess(record.get("Access"));
				roomCsvFile.setDisbledAccess(record.get("Disabled access"));
				csvList.add(roomCsvFile);

			}
		}catch (Exception e) {
			throw e;
		}finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return csvList;
	}

	@Value("${google.map.url}")
	String googleMapUrl;

	public Set<Site> getSitesCsvInputList(InputStream inputStream) throws Exception {
		Reader reader = null;
		Set<Site> csvList = new HashSet<Site>();
		Site sitesCsvFile;
		try {
			reader = new InputStreamReader(inputStream, UTF8CHARSET);
			Iterable<CSVRecord> records;
			records = CSVFormat.EXCEL.withHeader().parse(reader);

			for (CSVRecord record : records) {
				sitesCsvFile = new Site();
				sitesCsvFile.setSiteName(record.get("Site"));
				sitesCsvFile.setTown(record.get("Town"));
				sitesCsvFile.setLocality(record.get("Locality"));
				sitesCsvFile.setAddressLine1(record.get("Address line 1"));
				sitesCsvFile.setPostCode(record.get("Postcode"));
				sitesCsvFile.setGoogleMapLink(record.get("Google map link"));
				sitesCsvFile.setPhase(record.get("Phase"));
				csvList.add(sitesCsvFile);
			}
		}catch (Exception e) {
			throw e;
		}
		finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return csvList;
	}

	private String getLatitudeAndLongitude(String json){
		JSONArray resultArray = new JSONObject(json).getJSONArray("results");
		Double latitude=null;
		Double longitude=null;
		for(int i = 0; i < resultArray.length(); i++)
		{
			JSONObject mainObject = resultArray.getJSONObject(i);
			JSONObject geometry= mainObject.getJSONObject("geometry");
			JSONObject location= geometry.getJSONObject("location");
			latitude = location.getDouble("lat");
			longitude = location.getDouble("lng");
		}
		if(latitude != null && longitude != null){
			return latitude+"~"+longitude;
		}
		else{
			return null;
		}
	}
}