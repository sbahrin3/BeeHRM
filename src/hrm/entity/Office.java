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
public class Office extends Location {
	
	
	private int principal;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "contacts",
            joinColumns = {@JoinColumn(name = "office_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
	private List<Employee> contacts;
	
	@ManyToOne @JoinColumn(name="company_id")
	private Company company;
	

	
	public Office() {
		setId(lebah.util.UIDGenerator.getUID());
	}
	

	
	public boolean isPrincipal() {
		return principal == 1;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal ? 1 : 0;
	}
	

	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Employee> getContacts() {
		if ( contacts == null ) contacts = new ArrayList<>();
		return contacts;
	}

	public void setContacts(List<Employee> contacts) {
		this.contacts = contacts;
	}


	

}
