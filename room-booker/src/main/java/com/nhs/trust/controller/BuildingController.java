package com.nhs.trust.controller;
/**
 * Created by arif.mohammed on 30/10/2015.
 */

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nhs.trust.domain.Site;
import com.nhs.trust.service.BuildingService;
import com.nhs.trust.service.SiteService;
import com.nhs.trust.utils.LoadCsvUtil;

@RestController
public class BuildingController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(BuildingController.class);
	
	@Autowired
	BuildingService buildingService;

	@RequestMapping(value = "/buildings", method = {RequestMethod.GET})
	public String processGetAllBuildings() throws IOException, Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(buildingService.findAll());
	}

	@RequestMapping(value = "/building/{buildingId}", method = {RequestMethod.GET})
	public String getBuildingById(@PathVariable("buildingId") String siteId,
			ModelMap model) throws IOException, Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(buildingService.findById(Long.valueOf(siteId)));
	}
	
	@RequestMapping(value = "/building/site/{siteId}", method = {RequestMethod.GET})
	public String getBuildingById(@PathVariable("siteId") Long siteId,
			ModelMap model) throws IOException, Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(buildingService.findBySite(Long.valueOf(siteId)));
	}

}