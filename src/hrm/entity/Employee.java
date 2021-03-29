package hrm.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="employee")
public class Employee {
	
	@Id @Column(name="id", length=100)
	private String id;
	
	@Column(length=50)
	private String idNumber;
	
	@Column(length=150)
	private String name;
	
	@OneToMany (fetch = FetchType.LAZY, mappedBy="employee")
	private List<EmployeeJob> jobs = new ArrayList<>();
	
	@Embedded
    private Address address;
	
	
	
	public Employee() {
		setId(lebah.util.UIDGenerator.getUID());
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<EmployeeJob> getJobs() {
		Collections.sort(jobs, new SortByPrimaryJob());
		return jobs;
	}

	public void setJobs(List<EmployeeJob> jobs) {
		this.jobs = jobs;
	}
	
	static class SortByPrimaryJob implements Comparator<EmployeeJob> {

		@Override
		public int compare(EmployeeJob ej1, EmployeeJob ej2) {
			return ej1.isPrimaryJob() ? -1 : 1;
		}
		
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public Job getJob() {
		if ( jobs.size() > 0 ) 
			return jobs.get(0).getJob();
		return null;
	}
	
	public Department getDepartment() {
		if ( jobs.size() > 0 )
			return jobs.get(0).getDepartment();
		return null;
	}
	
	
}
