package com.nhs.trust.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ROOM_BOOKING")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomBooking {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="booking_Id")
	private long bookingId;
	@Column(name="user_id")
	private int userId;
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_email")
	private String userEmailId;
	
	private String title;
	private String description;

	@ManyToOne
	@JoinColumn(name="room_id", nullable=false)
	private Room room;

	@Column(name="room_type")
	private String roomType;

	@Column(name = "start_date_time")
	private Date startTime;

	@Column(name = "end_date_time")
	private Date endTime;

	private String status;

	@Column(name="booking_date")
	private Date bookingDate;

	@Column(name="cancel_date")
	private Date cancelDate;

	public long getBookingId() {
		return bookingId;
	}
	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomStatus() {
		return status;
	}
	public void setRoomStatus(String roomStatus) {
		this.status = roomStatus;
	}

	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @param buildingName
	 * @param fromDate
	 * @param toDate
	 * @return Specification<RoomBooking>
	 */
	public static Specification<RoomBooking> getBuildingsForDateRange(final String buildingName, final Date fromDate, final Date toDate) {
        return new Specification<RoomBooking>() {
            @Override
            public Predicate toPredicate(Root<RoomBooking> root, CriteriaQuery<?> rbcq, CriteriaBuilder rbcb) {
           	Predicate isBuildingName = rbcb.isTrue(root.get("room").get("building").in(buildingName)); 
            Predicate startTimeGeFromDate = rbcb.greaterThanOrEqualTo(root.<Date>get("startTime"),fromDate);
            Predicate endTimeGeFromDate = rbcb.lessThanOrEqualTo(root.<Date>get("endTime"),toDate);
            return rbcb.and(isBuildingName,startTimeGeFromDate,endTimeGeFromDate);
            }
        };
 
    }
	/**
	 * @param town
	 * @param site
	 * @param roomId
	 * @param title
	 * @param fromDate
	 * @param toDate
	 * @return Specification<RoomBooking>
	 */
	public static Specification<RoomBooking> getSearchCreteriaForReport(final String town, final String site, final Long roomId, final String title, final Date fromDate, final Date toDate) {
        return new Specification<RoomBooking>() {
            @Override
            public Predicate toPredicate(Root<RoomBooking> root, CriteriaQuery<?> rbcq, CriteriaBuilder rbcb) {
            	List<Predicate> predicates = new ArrayList<Predicate>();
            if (town != null && !town.isEmpty()){
            	predicates.add(rbcb.like(root.get("room").get("site").<String>get("town"), "%"+ town +"%"));
            }
            if (site != null && !site.isEmpty()){
            	predicates.add(rbcb.like(root.get("room").get("site").<String>get("site"), "%"+ site +"%"));
            }
            if (roomId != null && roomId.intValue() != 0){
            	predicates.add(rbcb.equal(root.get("room").get("roomId"), roomId));
            }
            if (title != null && !title.isEmpty()){
            	predicates.add(rbcb.like(root.<String>get("title"), "%"+ title +"%"));
            }
            if (fromDate != null){
            	predicates.add(rbcb.greaterThanOrEqualTo(root.<Date>get("startTime"),fromDate));
            }
            if (toDate != null){
            	predicates.add(rbcb.lessThanOrEqualTo(root.<Date>get("endTime"),toDate));
            }
            return rbcb.and(predicates.toArray(new Predicate[] {}));
            }
        };
 
    }

}
