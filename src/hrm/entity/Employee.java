package hrm.entity;

import java.util.ArrayList;
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
		return jobs;
	}

	public void setJobs(List<EmployeeJob> jobs) {
		this.jobs = jobs;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	
	
	
}
