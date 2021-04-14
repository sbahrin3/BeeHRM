package hrm.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}

	public Date getTimeOut() {
		return timeOut;
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

}
