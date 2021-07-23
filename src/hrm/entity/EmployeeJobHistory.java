package hrm.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="job_history")
public class EmployeeJobHistory {
	
	@Id @Column(length=50)
	private String id;
	
	@OneToOne
	private Employee employee;
	
	@OneToMany (fetch=FetchType.LAZY, mappedBy="jobHistory")
	private List<EmployeeJob> jobs;
	
	public EmployeeJobHistory() {
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

	public List<EmployeeJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<EmployeeJob> jobs) {
		this.jobs = jobs;
	}
	
	
	

}
