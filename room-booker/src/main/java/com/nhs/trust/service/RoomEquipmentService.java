package com.nhs.trust.service;

import java.util.List;

import com.nhs.trust.domain.RoomEquipment;

public interface RoomEquipmentService {

	List<RoomEquipment> findByRoom(Long roomId);

	void deleteByRoomId(long roomId);

}
