package hrm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
	
	public LeaveEntitlement() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
