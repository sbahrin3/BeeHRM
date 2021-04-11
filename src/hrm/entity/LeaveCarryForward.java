package hrm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="leave_carry")
public class LeaveCarryForward {
	
	@Id @Column(length=50)
	private String id;
	
	private int year;
	
	@ManyToOne @JoinColumn(name="employee_id")
	private Employee employee;

	
	@ManyToOne @JoinColumn(name="leave_id")
	private Leave leave;
	
	private int numberOfDays;
	
	public LeaveCarryForward() {
		setId(lebah.util.UIDGenerator.getUID());
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
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

	public int getNumberOfDays() {
		return numberOfDays;
	}


	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	
	@Override
	public boolean equals(Object o) {
		LeaveCarryForward x = (LeaveCarryForward) o;
		return x.getId().equals(getId());
	}


	@Override
	public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (getId() != null ? getId().hashCode() : 0);
        return hash;
	}
	
}
