package hrm.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="employee_job")
public class EmployeeJob {
	
	@Id @Column(name="id", length=100)
	private String id;

	@ManyToOne @JoinColumn(name="employee_id")
	private Employee employee;
	
	@ManyToOne @JoinColumn(name="department_id")
	private Department department;
	
	@ManyToOne @JoinColumn(name="job_id")
	private Job job;

	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Embedded
	private Salary salary;
	
	@OneToMany (fetch = FetchType.LAZY, mappedBy="employeeJob")
	private List<SalaryItem> salaryItems = new ArrayList<>();
	
	
	private int primaryJob;
	
	public EmployeeJob() {
		setId(lebah.util.UIDGenerator.getUID());
		salary = new Salary();
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
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public String getStartDateStr() {
		return startDate != null ? new SimpleDateFormat("dd/MM/yyyy").format(startDate) : "";
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getEndDateStr() {
		return endDate != null ? new SimpleDateFormat("dd/MM/yyyy").format(endDate) : "";
	}

	public Salary getSalary() {
		if ( salary == null ) salary = new Salary();
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}

	public boolean isPrimaryJob() {
		return primaryJob == 1;
	}

	public void setPrimaryJob(boolean primaryJob) {
		this.primaryJob = primaryJob ? 1 : 0;
	}

	public List<SalaryItem> getSalaryItems() {
		Collections.sort(salaryItems, new SortBySalaryItemOrder());
		return salaryItems;
	}

	public void setSalaryItems(List<SalaryItem> salaryItems) {
		this.salaryItems = salaryItems;
	}

	static class SortBySalaryItemOrder implements Comparator<SalaryItem> {

		@Override
		public int compare(SalaryItem s1, SalaryItem s2) {
			if ( s1.getOrderNo() < s2.getOrderNo() ) return - 1;
			else if ( s2.getOrderNo() > s2.getOrderNo() ) return 1;
			else return 0;
		}
		
	}
	
}
