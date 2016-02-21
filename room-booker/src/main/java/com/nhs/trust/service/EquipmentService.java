package com.nhs.trust.service;

import com.nhs.trust.domain.Equipment;

public interface EquipmentService extends CommonService<Equipment> {

	Equipment findByName(String name);

}
