package hrm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//TODO

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="employee_timesheet")
public class EmployeeTimesheet {
	
	@Id @Column(length=50)
	private String id;
	
	@ManyToOne @JoinColumn(name="employee_id")
	private Employee employee;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Temporal(TemporalType.TIME)
	private Date timeIn;
	
	@Temporal(TemporalType.TIME)
	private Date timeOut;
	
	@ManyToOne @JoinColumn(name="office_id")
	private Office office;
	
	public EmployeeTimesheet() {
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
	
	

}
