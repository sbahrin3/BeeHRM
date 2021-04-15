package hrm.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hrm.module.Util;
import lebah.db.entity.User;


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
	private List<EmployeeJob> jobs;
	
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
	
	@OneToMany (fetch=FetchType.LAZY, mappedBy="employee")
	private List<EmployeeLeave> employeeLeaves;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="employee")
	private List<LeaveCarryForward> leaveCarryForwards;
	
	@ManyToOne @JoinColumn(name="office_id")
	private Office office; //where this employee leave data will be based on
	
	@OneToOne @JoinColumn(name="user_id")
	private User user;
		
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
		if ( jobs == null ) jobs = new ArrayList<>();
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
	
	public EmployeeJob getPrimaryEmployeeJob() {
		if ( getJobs().size() == 0 ) return null;
		return getJobs().get(0);
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

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public LeaveEntitlement getLeaveEntitlement() {
		return leaveEntitlement;
	}

	public void setLeaveEntitlement(LeaveEntitlement leaveEntitlement) {
		this.leaveEntitlement = leaveEntitlement;
	}

	public List<EmployeeLeave> getEmployeeLeaves() {
		return employeeLeaves;
	}
	
	public List<LeaveCarryForward> getLeaveCarryForwards() {
		if ( leaveCarryForwards == null ) leaveCarryForwards = new ArrayList<>();
		return leaveCarryForwards;
	}
	
	public int getLeaveDaysCarryForward(Leave leave, int year) {
		Optional<LeaveCarryForward> optional =
                                        leaveCarryForwards.stream()
                                        .filter(c -> c.getYear() == year && c.getLeave().getId().equals(leave.getId()))
                                        .findFirst();
		if ( optional.isPresent())
			return optional.get().getNumberOfDays();
		return 0;
	}

	public void setLeaveCarryForwards(List<LeaveCarryForward> leaveCarryForwards) {
		this.leaveCarryForwards = leaveCarryForwards;
	}
	
	public void setEmployeeLeaves(List<EmployeeLeave> employeeLeaves) {
		this.employeeLeaves = employeeLeaves;
	}

	public int getLeaveDaysTaken(Leave leave) {
		return employeeLeaves.size() == 0 ? 0 : employeeLeaves.stream()
				.filter(l -> l.getLeave().getId().equals(leave.getId()))
				.collect(Collectors.summingInt(l -> l.getApprovedNumberOfDays()));
	}
	
	public List<EmployeeLeave> getAllEmployeeLeaves(int year) {
		Date startOfYear = Util.toDate("01/01/" + year);
		Date endOfYear = Util.toDate("31/12/" + year);
		return employeeLeaves.stream()
			.filter(l -> l.getApproveFromDate() != null && l.getApproveFromDate().after(startOfYear) && l.getApproveFromDate().before(endOfYear))
			.collect(Collectors.toList());
	}
	
	public List<EmployeeLeave> getApprovedEmployeeLeaves(int year) {
		Date startOfYear = Util.toDate("01/01/" + year);
		Date endOfYear = Util.toDate("31/12/" + year);
		return employeeLeaves.stream()
			.filter(l -> l.getApproveFromDate() != null && l.getStatus() == 2 && l.getApproveFromDate().after(startOfYear) && l.getApproveFromDate().before(endOfYear))
			.collect(Collectors.toList());
	}
	
	public int getLeaveDaysTaken(Leave leave, int year) {
		List<EmployeeLeave> filteredList =  getApprovedEmployeeLeaves(year);
		return filteredList.size() == 0 ? 0 : filteredList.stream()
				.filter(l -> l.getLeave().getId().equals(leave.getId()))
				.collect(Collectors.summingInt(l -> l.getApprovedNumberOfDays()));

	}
	
	public int getLeaveDaysEntitled(Leave leave, int year) {
		Optional<LeaveEntitlementItem> optional = getLeaveEntitlement().getItems().stream().filter(i -> i.getLeave().getId().equals(leave.getId())).findFirst();
		return optional.isPresent() ? optional.get().getNumberOfDays() : 0;
	}
	
	public int getLeaveDaysAvailable(Leave leave, int year) {
		int carryFwdCount = getLeaveDaysCarryForward(leave, year);
		int entitledCount = getLeaveDaysEntitled(leave, year);
		int takenCount = getLeaveDaysTaken(leave, year);
		return carryFwdCount + entitledCount - takenCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
