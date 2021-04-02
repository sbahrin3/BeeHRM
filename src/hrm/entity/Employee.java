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
	
	@Column(length=50)
	private String identityType; //MyKad, Passport
	@Column(length=100)
	private String identityNumber;
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	@Column(length=100)
	private String birthPlace;
	
	private int gender; //0-M, 1-F
	
	@ManyToOne @JoinColumn(name="leave_entitlement_id")
	private LeaveEntitlement leaveEntitlement;
		
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
		if ( address == null ) address = new Address();
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

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}
	
	public String getBirthDateStr() {
		return birthDate != null ? new SimpleDateFormat("dd/MM/yyyy").format(birthDate) : "";
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}
	

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public LeaveEntitlement getLeaveEntitlement() {
		return leaveEntitlement;
	}

	public void setLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
		this.leaveEntitlement = leaveEntitlement;
	}
	
	
}
