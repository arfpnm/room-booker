package com.nhs.trust.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhs.trust.common.TewvServiceException;
import com.nhs.trust.domain.Equipment;
import com.nhs.trust.repository.EquipmentRepository;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService{

	@Autowired
	private EquipmentRepository equipmentRepository;
	
	@Override
	public Equipment add(Equipment t) throws TewvServiceException {
		return equipmentRepository.save(t);
	}

	@Override
	public List<Equipment> findAll() {
		return equipmentRepository.findAll();
	}

	@Override
	public Equipment findById(long id) {
		return equipmentRepository.findOne(id);
	}
	
	@Override
	public Equipment findByName(String name) {
		return equipmentRepository.findByName(name);
	}
}
