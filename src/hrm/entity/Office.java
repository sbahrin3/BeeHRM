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
	
	@Column(length=150)
	private String name;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "contacts",
            joinColumns = {@JoinColumn(name = "office_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
	private List<Employee> contacts = new ArrayList<Employee>();
	
	@ManyToOne @JoinColumn(name="company_id")
	private Company company;
	
	@OneToOne @JoinColumn(name="district_id")
	private District district;

	@Column(length=200)
	private String address1;
	@Column(length=200)
	private String address2;
	@Column(length=10)
	private String postcode;
	
	@Column(length=100)
	private String email;
	@Column(length=20)
	private String telephone;
	@Column(length=20)
	private String fax;
	
	public Office() {
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public List<Employee> getContacts() {
		return contacts;
	}

	public void setContacts(List<Employee> contacts) {
		this.contacts = contacts;
	}
	
	
	

}
