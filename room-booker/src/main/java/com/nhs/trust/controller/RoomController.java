package com.nhs.trust.controller;
/*
 * Created by arif.mohammed on 30/10/2015.
 */

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nhs.trust.service.RoomBookingService;
import com.nhs.trust.service.RoomService;
import com.nhs.trust.service.SiteService;
import com.nhs.trust.utils.LoadCsvUtil;

@RestController
public class RoomController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(RoomController.class);

	@Autowired
	private RoomService roomService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private RoomBookingService bookingService;

	@Autowired
	private LoadCsvUtil csvUtil;

	@RequestMapping(value = "/rooms", method = {RequestMethod.GET})
	public String processGetAllRooms() throws Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(roomService.findAll());
	}

	@RequestMapping(value = "/rooms/{roomId}", method = {RequestMethod.GET})
	public String getRoomById(@PathVariable("roomId") String roomId,
			ModelMap model) throws Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(roomService.findById(Long.valueOf(roomId)));
	}

	@RequestMapping(value = "/rooms/user/{user}", method = {RequestMethod.GET})
	public String getRoomByUser(@PathVariable("user") String user,
			ModelMap model) throws Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(roomService.findByUser(user));
	}

	@RequestMapping(value = "/buildings/{buildingName}/rooms", method = {RequestMethod.GET})
	public String getRoomByBuilding(@PathVariable("buildingName") String buildingName,
			ModelMap model) throws Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(roomService.findByBuilding(buildingName));
	}

	@RequestMapping(value = "/sites-building-for-location", method = {RequestMethod.GET})
	public String getSiteAndBuilding() throws Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(roomService.findSiteAndBuildings());
	}

	@RequestMapping(value = "/add_room", method = RequestMethod.POST)
	public String addRoom(@RequestBody String addRoomJson, HttpServletRequest httpServletRequest)
			throws Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(roomService.addAndUpdateRelatedBookingData(addRoomJson));
	}
	
	@RequestMapping(value = "/upload-room-csv", method = {RequestMethod.POST})
	public String processUploadRoomFile(
			@RequestParam(value = "file") MultipartFile file) throws Exception {
		
		return roomService.uploadRoomFile(file);

	}
}