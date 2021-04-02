package hrm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="leave_entitlement")
public class LeaveEntitlement {
	
	@Id @Column(length=50)
	private String id;
	
	@Column(length=10)
	private String code;
	
	@Column(length=100)
	private String name;
	
	@Column(length=255)
	private String description;
	
	@OneToMany (fetch=FetchType.LAZY, mappedBy="leaveEntitlement")
	private List<LeaveEntitlementItem> items = new ArrayList<>();
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_leaves_entitlement",
            joinColumns = {@JoinColumn(name = "leave_entitlement_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
	private List<Employee> employees = new ArrayList<>();
	
	
	public LeaveEntitlement() {
		setId(lebah.util.UIDGenerator.getUID());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<LeaveEntitlementItem> getItems() {
		return items;
	}

	public void setItems(List<LeaveEntitlementItem> items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	

}
