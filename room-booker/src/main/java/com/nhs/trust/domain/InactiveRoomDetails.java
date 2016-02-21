package com.nhs.trust.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "INACTIVE_ROOMS")
public class InactiveRoomDetails {
	
	@Id
	@GeneratedValue
	private long id;
	
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="room_id")
    @JsonBackReference
    private Room room;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="status")
	private String status;
	
	@Column(name="cancel_existing_bookings")
	private String cancelExistingBooking;
	
	@Column(name="prevent_future_bookings")
	private String preventFutureBooking;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCancelExistingBooking() {
		return cancelExistingBooking;
	}

	public void setCancelExistingBooking(String cancelExistingBooking) {
		this.cancelExistingBooking = cancelExistingBooking;
	}

	public String getPreventFutureBooking() {
		return preventFutureBooking;
	}

	public void setPreventFutureBooking(String preventFutureBooking) {
		this.preventFutureBooking = preventFutureBooking;
	}

}
