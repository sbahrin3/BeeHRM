package hrm.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hrm.module.Params;
import hrm.module.Util;
import lebah.db.entity.Persistence;

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
	
	@Lob @Column(length=1000)
	private String remark;
	
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
	
	public Calendar getRequestFromDateCalendar() {
		Calendar c = Calendar.getInstance();
		c.setTime(requestFromDate);
		return c;
	}

	public Date getRequestToDate() {
		return requestToDate;
	}
	
	public String getRequestToDateStr() {
		return Util.toStr(requestToDate);
	}
	
	public Calendar getRequestToDateCalendar() {
		Calendar c = Calendar.getInstance();
		c.setTime(requestToDate);
		return c;
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
	
	public Calendar getApproveFromDateCalendar() {
		Calendar c = Calendar.getInstance();
		c.setTime(approveFromDate);
		return c;
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
	
	public Calendar getApproveToDateCalendar() {
		Calendar c = Calendar.getInstance();
		c.setTime(approveToDate);
		return c;
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
		if ( requestToDate == null || requestFromDate == null ) return 0;
		int holidays = Util.getNumberOfHolidays(requestFromDate, requestToDate, employee);
		int weekends = Util.getNumberOfWeekends(requestFromDate, requestToDate);
		int days = Util.numberOfDaysBetween(requestFromDate, requestToDate);
		
		return days - holidays - weekends;
	}
	
	public int getApprovedNumberOfDays() {
		if ( approveToDate == null || approveFromDate == null ) return 0;
		int holidays = Util.getNumberOfHolidays(approveFromDate, approveToDate, employee);
		int weekends = Util.getNumberOfWeekends(approveFromDate, approveToDate);
		int days = Util.numberOfDaysBetween(approveFromDate, approveToDate);
		return days - holidays - weekends;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public int getApproveFromDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(approveFromDate);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getApproveToDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(approveToDate);
		return c.get(Calendar.DAY_OF_MONTH);
	}

}
