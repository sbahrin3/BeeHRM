package hrm.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name="event_calendar")
public class EventCalendar {
	
	@Id @Column(length=50)
	private String id;
	
	@Temporal(TemporalType.DATE)
	private Date fromDate;
	
	@Temporal(TemporalType.DATE)
	private Date toDate;
	
	private int holiday;
	
	@Column(length=50)
	private String code;
	
	@Column(length=100)
	private String name;
	
	@Column(length=200)
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "calendar_states",
	    joinColumns = {@JoinColumn(name = "event_id")},
	    inverseJoinColumns = {@JoinColumn(name = "state_id")}
	)
	private List<State> states;
	
	
	public EventCalendar() {
		setId(lebah.util.UIDGenerator.getUID());
	}
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public Date getFromDate() {
		return fromDate;
	}
	
	public Calendar getFromDateCalendar() {
		Calendar c = Calendar.getInstance();
		c.setTime(fromDate);
		return c;
	}
	
	public String getFromDateStr() {
		return fromDate != null ? Util.toStr(fromDate) : "";
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}
	
	public Calendar getToDateCalendar() {
		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		return c;
	}

	public String getToDateStr() {
		return toDate != null ? Util.toStr(toDate) : "";
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public boolean isHoliday() {
		return holiday == 1;
	}


	public void setHoliday(boolean holiday) {
		this.holiday = holiday ? 1 : 0;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<State> getStates() {
		if (states == null ) states = new ArrayList<State>();
		return states;
	}


	public void setStates(List<State> states) {
		this.states = states;
	}
	
	public int getNumberOfDays() {
		return Util.numberOfDaysBetween(fromDate, toDate);
	}
	
	@Override
	public boolean equals(Object o) {
		EventCalendar m = (EventCalendar) o;
		return m.getId().equals(getId());
	}
	
	@Override
	public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (getId() != null ? getId().hashCode() : 0);
        return hash;
	}
	
	public boolean hasState(State state) {
		if ( states == null ) return false;
		return states.stream().filter(s -> s.getId().equals(state.getId())).findAny().isPresent();
	}

	public int getFromDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(fromDate);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getToDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		return c.get(Calendar.DAY_OF_MONTH);
	}	
	
	
	
}
