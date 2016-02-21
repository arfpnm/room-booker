package com.nhs.trust.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nhs.trust.domain.Equipment;
import com.nhs.trust.service.EquipmentService;

@RestController
public class EquipmentController extends CommonController{

	@Autowired
	private EquipmentService equipmentService;
	
	@RequestMapping(value = "/get-equipment", method = RequestMethod.GET)
	public Set<Equipment> getEquipment()
			throws Exception {
		return new HashSet<Equipment>(equipmentService.findAll());
	}
}
