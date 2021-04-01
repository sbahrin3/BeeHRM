package hrm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="leave_process")
public class LeaveProcess {
	
	@Id @Column(length=50)
	private String id;
	
	@OneToOne @JoinColumn(name="manager_id")
	private Employee manager;
	
	@OneToOne @JoinColumn(name="employee_leave_id")
	private EmployeeLeave employeeLeave;
	
	@Temporal(TemporalType.DATE)
	private Date openDate;
	
	@Temporal(TemporalType.DATE)
	private Date closeDate;
	
	@Column(length=255)
	private String remarks;
	
	
	public LeaveProcess() {
		setId(lebah.util.UIDGenerator.getUID());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public EmployeeLeave getEmployeeLeave() {
		return employeeLeave;
	}

	public void setEmployeeLeave(EmployeeLeave employeeLeave) {
		this.employeeLeave = employeeLeave;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
