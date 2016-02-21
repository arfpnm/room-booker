package com.nhs.trust.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhs.trust.domain.RoomEquipment;
import com.nhs.trust.repository.RoomEquipmentRepository;

@Service
@Transactional
public class RoomEquipmentServiceImpl implements RoomEquipmentService{

	@Autowired
	private RoomEquipmentRepository roomEquipmentRepository;
	
	@Override
	public List<RoomEquipment> findByRoom(Long roomId) {
		return roomEquipmentRepository.findByRoom(roomId);
	}
	
	@Override
	public void deleteByRoomId(long roomId){
		roomEquipmentRepository.deleteByRoomId(roomId);
	}
}
