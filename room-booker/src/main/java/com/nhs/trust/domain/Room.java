package com.nhs.trust.domain;

/*
 * Created by arif.mohammed on 30/10/2015.
 */

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ROOM",
        uniqueConstraints = @UniqueConstraint(columnNames = {"b_id", "room_name", "capacity"}))
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "room_id")
    @GeneratedValue
    private long roomId;

    @OneToOne
    @JoinColumn(name = "b_id")
    private Building building;
    
    private String siteName;

    @Column(name = "room_name")
    private String roomName;
    private String contact;

    @Column(name = "team_owner")
    private String teamOwner;

    @Column(name = "individual_owner")
    private String individualOwner;

    @Column(name = "room_type")
    private String roomType;
    private String category;
    
    @OneToMany(mappedBy="room", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonManagedReference
    private Set<InactiveRoomDetails> inactiveRooms;
    
    @OneToMany(mappedBy="room", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonManagedReference
    private Set<RoomEquipment> equipment;
    
    private String capacity;
    private String catering;
    private String comments;
    private String access;

    @Column(name = "disabled_access")
    private String disbledAccess;

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTeamOwner() {
        return teamOwner;
    }

    public void setTeamOwner(String teamOwner) {
        this.teamOwner = teamOwner;
    }

    public String getIndividualOwner() {
        return individualOwner;
    }

    public void setIndividualOwner(String individualOwner) {
        this.individualOwner = individualOwner;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCapacity() {
        return capacity;
    }

	public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCatering() {
        return catering;
    }

    public void setCatering(String catering) {
        this.catering = catering;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getDisbledAccess() {
        return disbledAccess;
    }

    public void setDisbledAccess(String disbledAccess) {
        this.disbledAccess = disbledAccess;
    }

    public Set<InactiveRoomDetails> getInactiveRooms() {
		return inactiveRooms;
	}

	public void setInactiveRooms(Set<InactiveRoomDetails> inactiveRooms) {
		this.inactiveRooms = inactiveRooms;
	}
	
	public Set<RoomEquipment> getEquipment() {
		return equipment;
	}

	public void setEquipment(Set<RoomEquipment> equipment) {
		this.equipment = equipment;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((building == null) ? 0 : building.hashCode());
		result = prime * result
				+ ((capacity == null) ? 0 : capacity.hashCode());
		result = prime * result
				+ ((roomName == null) ? 0 : roomName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (!(building instanceof Building))
			return false;	
		if (obj == this)
			return true;
		if(building != null && ((Room) obj).building != null){
			return this.building.getBuildingName().equals(((Room) obj).getBuilding().getBuildingName());
		}
	
		if (building == null) {
			if (other.building != null)
				return false;
		} else if (!building.equals(other.building))
			return false;
		
		
		if (capacity == null) {
			if (other.capacity != null)
				return false;
		} else if (!capacity.equals(other.capacity))
			return false;
		if (roomName == null) {
			if (other.roomName != null)
				return false;
		} else if (!roomName.equals(other.roomName))
			return false;
		return true;
	}

}