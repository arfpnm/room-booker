package com.nhs.trust.controller;
/*
 * Created by arif.mohammed on 30/10/2015.
 */

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhs.trust.domain.Room;
import com.nhs.trust.domain.RoomBooking;
import com.nhs.trust.service.MailService;
import com.nhs.trust.service.RoomBookingService;
import com.nhs.trust.service.RoomService;

@RestController
public class RoomBookingController extends CommonController{

	private static final String INVALID_ROOM="Room details not found, please enter a valid room";
	private static final Logger logger = LoggerFactory.getLogger(RoomBookingController.class);

	@Autowired
	private RoomBookingService roomBookingService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private MailService mailService;

	@Value("${from}")
	private String from;
	@Value("${to}")
	private String to;
	@Value("${msg}")
	private String msg;
	@Value("${subject}")
	private String subject;
	@Value("${is.mail.enabled}")
	private String mailEnabled;

	/**
	 * @return List<RoomBooking>
	 * @throws Exception
	 */
	@RequestMapping(value = "/bookings", method = RequestMethod.GET)
	public List<RoomBooking> getAll()
			throws Exception {
		return roomBookingService.findAll();
	}

	/**
	 * @param bookingId
	 * @return RoomBooking
	 * @throws Exception
	 */
	@RequestMapping(value = "/bookings/{bookingId}", method = RequestMethod.GET)
	public RoomBooking getById(@PathVariable("bookingId") String bookingId)
			throws Exception {
		Long bookingIdLong = Long.valueOf(bookingId);
		return roomBookingService.findById(bookingIdLong);
	}

	/**
	 * @param buildingName
	 * @return List<RoomBooking>
	 * @throws Exception
	 */
	@RequestMapping(value = "/bookings/building/{buildingName}", method = RequestMethod.GET)
	public List<RoomBooking> getByBuildingName(@PathVariable("buildingName") String buildingName)
			throws Exception {
		return roomBookingService.findByBuilding(buildingName);
	}

	/**
	 * @param buildingDateRangeJson
	 * @param httpServletRequest
	 * @return List<RoomBooking>
	 * @throws Exception
	 */
	@RequestMapping(value = "/bookings/building/date-range", method = RequestMethod.POST)
	public List<RoomBooking> getByBuildingAndDateRange(@RequestBody String buildingDateRangeJson, HttpServletRequest httpServletRequest)
			throws Exception {
		JSONObject jsonObject = new JSONObject(buildingDateRangeJson);
		Date startDate = new Date(jsonObject.getLong("start-date"));
		Date endDate = new Date(jsonObject.getLong("end-date"));
		return roomBookingService.findByBuildingWithDateRange(jsonObject.getString("building"), startDate, endDate);
	}

	/**
	 * @param roomDateRangeJson
	 * @param httpServletRequest
	 * @return List<RoomBooking>
	 * @throws Exception
	 */
	@RequestMapping(value = "/bookings/room/date-range", method = RequestMethod.POST)
	public List<RoomBooking> getByRoomAndDateRange(@RequestBody String roomDateRangeJson, HttpServletRequest httpServletRequest)
			throws Exception {
		JSONObject jsonObject = new JSONObject(roomDateRangeJson);
		Date startDate = new Date(jsonObject.getLong("start-date"));
		Date endDate = new Date(jsonObject.getLong("end-date"));
		return roomBookingService.findByRoomWithDateRange(jsonObject.getLong("room"), startDate, endDate);
	}


	/**
	 * @param roomDateRangeJson
	 * @param httpServletRequest
	 * @return List<RoomBooking>
	 * @throws Exception
	 */
	@RequestMapping(value = "/bookings/search/report", method = RequestMethod.POST)
	public List<RoomBooking> searchCriteriaForReporting(@RequestBody String roomReportSearchCriteriaJson, HttpServletRequest httpServletRequest)
			throws Exception {

		JSONObject jsonObject = new JSONObject(roomReportSearchCriteriaJson);
		String town = jsonObject.getString("town");
		String site = jsonObject.getString("site");
		Long roomId = jsonObject.getLong("roomId");
		String title = jsonObject.getString("title");
		long stDateLong = jsonObject.getLong("start-date");
		long enDateLong = jsonObject.getLong("end-date");
		Date fromDate = stDateLong == 0 ? null : new Date(stDateLong);
		Date toDate = enDateLong == 0 ? null : new Date(enDateLong);
		return roomBookingService.searchRoomBookingForReporting(town, site,roomId, title, fromDate, toDate);
	}
	/**
	 * @param roomId
	 * @return List<RoomBooking>
	 * @throws Exception
	 */
	@RequestMapping(value = "/bookings/room/{roomId}", method = RequestMethod.GET)
	public List<RoomBooking> getByRoom(@PathVariable("roomId") String roomId)
			throws Exception {
		return roomBookingService.findByRoom(roomId);
	}

	/**
	 * @param addBookingJson
	 * @return RoomBooking
	 * @throws Exception
	 */
	@RequestMapping(value = "/room-booking/add-update-room-booking", method = RequestMethod.POST)
	public RoomBooking addRoomBooking(@RequestBody String addBookingJson)
			throws Exception {
		RoomBooking roomBooking = new ObjectMapper().readValue(addBookingJson, RoomBooking.class);
		Room room = roomService.findById(new JSONObject(addBookingJson).getLong("roomId")); 
		roomBooking.setRoom(room);
		roomBooking.setBookingDate(new Date());
		if(room != null){
			roomBooking = roomBookingService.add(roomBooking);
		}
		else{
			throw new Exception(INVALID_ROOM);
		}
		return roomBooking;
	}

	@RequestMapping(value = "/room-booking/cancel-booking", method = RequestMethod.POST)
	public RoomBooking cancelRoomBooking(@RequestBody String bookingIdJson)
			throws Exception {
		return roomBookingService.cancel(new JSONObject(bookingIdJson).getLong("bookingId"));
	}

}