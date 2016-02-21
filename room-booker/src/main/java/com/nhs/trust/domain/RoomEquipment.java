package com.nhs.trust.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ROOM_EQUIPMENT")
public class RoomEquipment {
	
	@Id
	@GeneratedValue
	private long id;
	
	private long quantity;
	
	@OneToOne(cascade=CascadeType.ALL)  
	@JoinColumn(name="equipment_id")  
	private Equipment roomEquipment;
	
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="room_id")
    @JsonBackReference
    private Room room;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Equipment getRoomEquipment() {
		return roomEquipment;
	}

	public void setRoomEquipment(Equipment roomEquipment) {
		this.roomEquipment = roomEquipment;
	}
	
}
