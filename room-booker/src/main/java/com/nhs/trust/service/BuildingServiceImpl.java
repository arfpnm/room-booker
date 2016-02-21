package com.nhs.trust.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhs.trust.common.TewvServiceException;
import com.nhs.trust.domain.Building;
import com.nhs.trust.domain.RoomBooking;
import com.nhs.trust.repository.BuildingRepository;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService{

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Override
	public Building add(Building t) throws TewvServiceException {
		return buildingRepository.save(t);
	}

	@Override
	public List<Building> findAll() {
		return buildingRepository.findAll();
	}

	@Override
	public Building findById(long id) {
		return buildingRepository.findOne(id);
	}
	
	@Override
	public List<Building> findBySite(long siteId){
		return buildingRepository.findBySite(siteId);
	}
	
	@Override
	public Building findByName(String buildingName){
		return buildingRepository.findByName(buildingName);
	}
	
	@Override
	public List<Building> addBatch(Set<Building> buildingEntities) {
    	return buildingRepository.save(buildingEntities);
    }

	
}
