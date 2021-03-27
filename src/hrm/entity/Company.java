package hrm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="company")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "branch_type", length=4) //HQ = head quaters; SUB = Subsidiary
public abstract class Company {
	
	@Id @Column(name="id", length=100)
	private String id;
	
	@Column(length=150)
	private String name;
	
	@Column(length=50)
	private String registrationNumber;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="company")
	private List<Office> offices = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="parent")
	private List<Company> branches = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="company")
	private List<Department> departments = new ArrayList<>();
	

	@ManyToOne  @JoinColumn(name="parent_id")
	private Company parent;
	
	
	public Company() {
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

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public List<Office> getOffices() {
		return offices;
	}

	public void setOffices(List<Office> offices) {
		this.offices = offices;
	}

	public List<Company> getBranches() {
		return branches;
	}

	public void setBranches(List<Company> branches) {
		this.branches = branches;
	}
	
	

	public List<Department> getDepartments() {
		return departments;
	}


	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}


	public Company getParent() {
		return parent;
	}

	public void setParent(Company parent) {
		this.parent = parent;
	}


	public boolean isHQ() {
		return this instanceof CompanyHQ;
	}

	public boolean isSubsidiary() {
		return this instanceof CompanySubsidiary;
	}


}
