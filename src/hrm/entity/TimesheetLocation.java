package hrm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hrm.module.Util;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="timesheet_location")
public class TimesheetLocation {
	
	@Id @Column(name="id", length=50)
	private String id;
	
	@ManyToOne @JoinColumn(name="timesheet_id")
	private Timesheet timesheet;
	
	@Temporal(TemporalType.TIME)
	private Date timeIn;
	
	@Temporal(TemporalType.TIME)
	private Date timeOut;
	
	//@OneToOne @JoinColumn(name="client_id")
	//private Client client;
	
	@OneToOne @JoinColumn(name="location_id")
	private Location location;
	
	public TimesheetLocation() {
		setId(lebah.util.UIDGenerator.getUID());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timesheet getTimesheet() {
		return timesheet;
	}

	public void setTimesheet(Timesheet timesheet) {
		this.timesheet = timesheet;
	}

	public Date getTimeIn() {
		return timeIn;
	}
	
	public String getTimeInStr() {
		return Util.toTimeStr(timeIn);
	}
	
	public String getTimeInStr1() {
		return getTimeInStr().length() > 4 ? getTimeInStr().substring(0, 5) : "";
	}
	
	public String getTimeInStr2() {
		return getTimeInStr().length() > 5 ? getTimeInStr().substring(6) : "";
	}

	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}

	public Date getTimeOut() {
		return timeOut;
	}
	
	public String getTimeOutStr() {
		return Util.toTimeStr(timeOut);
	}
	
	public String getTimeOutStr1() {
		return getTimeOutStr().length() > 4 ? getTimeOutStr().substring(0, 5) : "";
	}
	
	public String getTimeOutStr2() {
		return  getTimeOutStr().length() > 5 ? getTimeOutStr().substring(6) : "";
	}

	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	/*
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	*/
	
	
	
	
	
}
