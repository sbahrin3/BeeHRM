package hrm.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="employee_leave")
public class EmployeeLeave {
	
	@Id @Column(length=50)
	private String id;
	
	@ManyToOne @JoinColumn(name="leave_id")
	private Leave leave;
	
	private int status; //0-request, 1-process, 2-approved, 3-rejected
	
	@ManyToOne @JoinColumn(name="employee_id")
	private Employee employee;
	
	@Temporal(TemporalType.DATE)
	private Date requestDate;
	
	@Temporal(TemporalType.DATE)
	private Date requestFromDate;
	
	@Temporal(TemporalType.DATE)
	private Date requestToDate;
	
	@Temporal(TemporalType.DATE)
	private Date approveDate;
	
	@Temporal(TemporalType.DATE)
	private Date approveFromDate;
	
	@Temporal(TemporalType.DATE)
	private Date approveToDate;
	
	private int totalDays;
	
	
	public EmployeeLeave() {
		setId(lebah.util.UIDGenerator.getUID());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
	}

	public Date getRequestDate() {
		return requestDate;
	}
	
	public String getRequestDateStr() {
		return Util.toStr(requestDate);
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getRequestFromDate() {
		return requestFromDate;
	}
	
	public String getRequestFromDateStr() {
		return Util.toStr(requestFromDate);
	}

	public void setRequestFromDate(Date requestFromDate) {
		this.requestFromDate = requestFromDate;
	}

	public Date getRequestToDate() {
		return requestToDate;
	}
	
	public String getRequestToDateStr() {
		return Util.toStr(requestToDate);
	}

	public void setRequestToDate(Date requestToDate) {
		this.requestToDate = requestToDate;
	}

	public Date getApproveDate() {
		return approveDate;
	}
	
	public String getApproveDateStr() {
		return Util.toStr(approveDate);
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public Date getApproveFromDate() {
		return approveFromDate;
	}
	
	public String getApproveFromDateStr() {
		return Util.toStr(approveFromDate);
	}

	public void setApproveFromDate(Date approveFromDate) {
		this.approveFromDate = approveFromDate;
	}

	public Date getApproveToDate() {
		return approveToDate;
	}

	public String getApproveToDateStr() {
		return Util.toStr(approveToDate);
	}
	
	public void setApproveToDate(Date approveToDate) {
		this.approveToDate = approveToDate;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}	
	
	public int getRequestedNumberOfDays() {

		long diffInMillies = Math.abs(requestToDate.getTime() - requestFromDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		return (int) diff;
	}
	
	public String getLeaveStatusStr() {
		switch (status) {
			case 0:
				return "New";
			case 1:
				return "Process";
			case 2:
				return "Approved";
			case 3:
				return "Rejected";
				
		}
		return "";
	}

}
