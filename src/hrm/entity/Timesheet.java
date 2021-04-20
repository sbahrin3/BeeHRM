package hrm.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="timesheet")
public class Timesheet {
	
	@Id @Column(name="id", length=200)
	private String id;
	
	@ManyToOne @JoinColumn(name="employee_id")
	private Employee employee;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	@Temporal(TemporalType.TIME)
	private Date timeIn;
	@Temporal(TemporalType.TIME)
	private Date timeOut;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "timesheet_projects",
	    joinColumns = {@JoinColumn(name = "timesheet_id")},
	    inverseJoinColumns = {@JoinColumn(name = "project_id")}
	)
	private List<Project> projects;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="timesheet", cascade=CascadeType.PERSIST)
	private List<TimesheetLocation> locations;
	
	
	public Timesheet() {
		setId(lebah.util.UIDGenerator.getUID());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getDate() {
		return date;
	}
	
	public String getDateStr() {
		return Util.toStr(date);
	}

	public void setDate(Date date) {
		this.date = date;
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

	public List<Project> getProjects() {
		if ( projects == null ) projects = new ArrayList<>();
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public boolean hasProject(Project project) {
		return projects.size() > 0 ? projects.stream()
                                             .filter(p -> p.getId().equals(project.getId()))
                                             .findAny()
                                             .isPresent() : false;
	}
	
	public List<TimesheetLocation> getLocations() {
		if ( locations == null ) locations = new ArrayList<>();
		Collections.sort(locations, new SortLocationsByTimeIn());
		return locations;
	}

	public void setLocations(List<TimesheetLocation> locations) {
		this.locations = locations;
	}

	@Override
	public boolean equals(Object o) {
		Timesheet t = (Timesheet) o;
		return t.getId().equals(getId());
	}
	
	@Override
	public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (getId() != null ? getId().hashCode() : 0);
        return hash;
	}	
	
	static class SortLocationsByTimeIn implements Comparator<TimesheetLocation> {
		@Override
		public int compare(TimesheetLocation m1, TimesheetLocation m2) {
			return m1.getTimeIn().compareTo(m2.getTimeIn());
		}
		
	}

}
