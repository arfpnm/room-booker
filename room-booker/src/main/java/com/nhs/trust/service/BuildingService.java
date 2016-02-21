package com.nhs.trust.service;

import java.util.List;
import java.util.Set;

import com.nhs.trust.domain.Building;

public interface BuildingService extends CommonService<Building> {

	List<Building> findBySite(long siteId);

	Building findByName(String buildingName);

	List<Building> addBatch(Set<Building> buildingEntities);
}
