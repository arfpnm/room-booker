package com.nhs.trust.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment {
	
	 	@Id
	    @Column(name = "equipment_id")
	    @GeneratedValue
	    private long equipmentId;
	 	
	 	private String equipment;
	 	 
	 	public long getEquipmentId() {
			return equipmentId;
		}

		public String getEquipment() {
			return equipment;
		}

		public void setEquipment(String equipment) {
			this.equipment = equipment;
		}

}
