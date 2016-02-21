package com.nhs.trust.controller;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhs.trust.domain.Holiday;
import com.nhs.trust.service.HolidayService;

@RestController
public class HolidayController extends CommonController{

	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${uk.holiday.list}")
	private String holidayUrl;
	
	@RequestMapping(value = "/add-uk-holidays", method = RequestMethod.POST)
	public Set<Holiday> addUkHolidays(@RequestBody String holidayCriteriaJson, HttpServletRequest httpServletRequest)
			throws Exception {
		
		Set<Holiday> holidayList;
		JSONObject jsonObject = new JSONObject(holidayCriteriaJson);
		String startYear = jsonObject.getString("start_year");
		String endYear = jsonObject.getString("end_year");
		String url = MessageFormat.format(holidayUrl, startYear, endYear);

		ResponseEntity<String>  get_holidays =
				restTemplate.postForEntity(url, null, String.class);
		holidayList = new ObjectMapper().readValue(get_holidays.getBody(), new TypeReference<Set<Holiday>>() {});
		return holidayService.addBatch(holidayList);
	}
	
	@RequestMapping(value = "/holiday-for-year/{year}", method = RequestMethod.GET)
	public List<Holiday> getHolidaysByYear(@PathVariable Integer year)
			throws Exception {
		return holidayService.findByYear(year);
	}
	
	@RequestMapping(value = "/holiday-for-year-month/{year}/{month}", method = RequestMethod.GET)
	public List<Holiday> getHolidaysByYear(@PathVariable Integer year, @PathVariable Integer month)
			throws Exception {
		return holidayService.findByYearAndMonth(year, month);
	}
	
	@RequestMapping(value = "/holiday-for-year-month-day/{year}/{month}/{day}", method = RequestMethod.GET)
	public List<Holiday> getHolidaysByYear(@PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day)
			throws Exception {
		return holidayService.findByYearMonthAndDay(year, month, day);
	}
}
