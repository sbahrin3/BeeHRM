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
	
	@ManyToOne @JoinColumn(name="company_id")
	private Company company;
	
	@ManyToOne @JoinColumn(name="job_id")
	private Job job;

	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@OneToOne @JoinColumn(name="salary_config_id")
	private SalaryConfig salaryConfig; 
	
	public EmployeeJob() {
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public SalaryConfig getSalaryConfig() {
		return salaryConfig;
	}

	public void setSalaryConfig(SalaryConfig salaryConfig) {
		this.salaryConfig = salaryConfig;
	}
	
	public double getGrossSalaryAmount() {
		return this.salaryConfig.getGrossAmount();
	}
	
	public double getAllowanceAmount() {
		return this.salaryConfig.getAllowanceAmount();
	}
	
	public double getNetSalaryAmount() {
		return this.salaryConfig.getNetAmount();
	}
	
	public double getDeductionsAmount() {
		return this.salaryConfig.calculateDeductionAmount();
	}
	
}
