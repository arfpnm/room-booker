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
import com.nhs.trust.service.SiteService;
import com.nhs.trust.utils.LoadCsvUtil;

@RestController
public class SiteController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(SiteController.class);

	@Autowired
	private SiteService siteService;

	@Autowired
	private LoadCsvUtil csvUtil;

	@RequestMapping(value = "/sites", method = {RequestMethod.GET})
	public String processGetAllSites() throws IOException, Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(siteService.findAll());
	}

	@RequestMapping(value = "/sites/{siteId}", method = {RequestMethod.GET})
	public String getSiteById(@PathVariable("siteId") String siteId,
			ModelMap model) throws IOException, Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(siteService.findById(Long.valueOf(siteId)));
	}

	@RequestMapping(value = "/upload-site-csv", method = {RequestMethod.POST})
	public String processUploadSiteFile(
			@RequestParam(value = "file") MultipartFile file) throws IOException, Exception {

		Set<Site> csvList = csvUtil.getSitesCsvInputList(file.getInputStream());
		Set<Site> siteList = new HashSet<Site>();
		Site existingSite = null;
		for (Site inputRecord : csvList) {
			existingSite=siteService.findByLocationAndTown(inputRecord.getSiteName(), inputRecord.getLocality(), inputRecord.getTown());
			if(existingSite != null){
				inputRecord.setSiteId(existingSite.getSiteId());
			}
			siteList.add(inputRecord);
		}
		if(siteList != null && siteList.size() > 0){
			siteService.addBatch(siteList);
		}
		return "success";
	}

}