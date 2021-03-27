package hrm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="office")
public class Office {
	
	@Id @Column(name="id", length=100)
	private String id;
	
	private int principal;
	
	@Column(length=150)
	private String name;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "contacts",
            joinColumns = {@JoinColumn(name = "office_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
	private List<Employee> contacts = new ArrayList<Employee>();
	
	@ManyToOne @JoinColumn(name="company_id")
	private Company company;
	
	@Embedded
    private Address address;
	
	public Office() {
		setId(lebah.util.UIDGenerator.getUID());
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isPrincipal() {
		return principal == 1;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal ? 1 : 0;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Employee> getContacts() {
		return contacts;
	}

	public void setContacts(List<Employee> contacts) {
		this.contacts = contacts;
	}

	public Address getAddress() {
		if ( address == null ) address = new Address();
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	

}
