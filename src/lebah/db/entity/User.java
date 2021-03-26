package lebah.db.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lebah.util.PasswordService;


/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="users")
public class User {
	
	@Id @Column(name="id", length=200)
	private String id;
	@Column(length=100)
	private String userName;
	@Column(length=100)
	private String userPassword;
	@Column(length=100)
	private String firstName;
	@Column(length=100)
	private String lastName;
	@ManyToOne @JoinColumn(name="role_id")
	private Role role;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
	private List<Role> secondaryRoles = new ArrayList<Role>();
	@Column(length=100)
	private String avatarImageFileName;
	@Column(length=100)
	private String signatureImageFileName;
	
	@Column(length=50)
	private String position;
	@Column(length=100)
	private String department;	
	@Column(length=50)
	private String telephone;
	@Column(length=50)
	private String fax;
	@Column(length=50)
	private String email;
	@Column(length=10)
	private String initial;
	@Column(length=50)
	private String pageStyle;
	
	@Column(length=100)
	private String companyName;
	@Column(length=100)
	private String companyRegistrationNo;
	@Column(length=100)
	private String bankName;
	@Column(length=100)
	private String bankAcctNo;
	
	@Column(length=200)
	private String documentFileName1;
	@Column(length=200)
	private String documentFileName2;
	@Column(length=200)
	private String documentFileName3;
	@Column(length=200)
	private String documentFileName4;
	@Column(length=200)
	private String documentFileName5;
	
	public User() {
		setId(lebah.util.UIDGenerator.getUID());
	}
	
	public User(String userName, String userPassword, String firstName, String lastName) {
		setId(userName);
		setUserName(userName);
		setUserPassword(userPassword);
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		try {
			userPassword = PasswordService.encrypt(userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.userPassword = userPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Role> getSecondaryRoles() {
		return secondaryRoles;
	}

	public void setSecondaryRoles(List<Role> secondaryRoles) {
		this.secondaryRoles = secondaryRoles;
	}

	public String getAvatarImageFileName() {
		return avatarImageFileName;
	}

	public void setAvatarImageFileName(String avatarImageFileName) {
		this.avatarImageFileName = avatarImageFileName;
	}

	public String getSignatureImageFileName() {
		return signatureImageFileName;
	}

	public void setSignatureImageFileName(String signatureImageFileName) {
		this.signatureImageFileName = signatureImageFileName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getInitial() {
		if ( initial == null || "".equals(initial)) {
			try {
				String name = firstName + " " + lastName;
				String i1 = name.substring(0, 1);
				String i2 = "";
				int _space = name.indexOf(" ");
				if ( _space > 0 ) {
					i2 = name.substring(_space + 1, _space + 2);
				}
				initial = i1 + i2;
			} catch ( Exception e ) { e.printStackTrace(); }
		}
		return initial;
	}

	public void setInitial(String initial) {
		if ( initial == null || "".equals(initial)) {
			try {
				String name = firstName + " " + lastName;
				String i1 = name.substring(0, 1);
				String i2 = "";
				int _space = name.indexOf(" ");
				if ( _space > 0 ) {
					i2 = name.substring(_space + 1, _space + 2);
				}
				initial = i1 + i2;
			} catch ( Exception e ) { e.printStackTrace(); }
		}
		this.initial = initial;
	}

	public boolean hasRole(String roleId) {
		if ( role == null || roleId == null) return false;
		if ( role.getId().equals(roleId)) return true;
		Optional<Role> optional = secondaryRoles.stream().filter(r -> r.getId().equals(roleId)).findFirst();
		return optional.isPresent();
	}
	
	public boolean hasRole(Role role) {
		return role != null ? hasRole(role.getId()) : false;
	}

	public String getPageStyle() {
		if ( pageStyle == null || "".equals(pageStyle)) pageStyle = "default.css";
		return pageStyle;
	}

	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyRegistrationNo() {
		return companyRegistrationNo;
	}

	public void setCompanyRegistrationNo(String companyRegistrationNo) {
		this.companyRegistrationNo = companyRegistrationNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAcctNo() {
		return bankAcctNo;
	}

	public void setBankAcctNo(String bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}

	public String getDocumentFileName1() {
		return documentFileName1;
	}

	public void setDocumentFileName1(String documentFileName1) {
		this.documentFileName1 = documentFileName1;
	}

	public String getDocumentFileName2() {
		return documentFileName2;
	}

	public void setDocumentFileName2(String documentFileName2) {
		this.documentFileName2 = documentFileName2;
	}

	public String getDocumentFileName3() {
		return documentFileName3;
	}

	public void setDocumentFileName3(String documentFileName3) {
		this.documentFileName3 = documentFileName3;
	}

	public String getDocumentFileName4() {
		return documentFileName4;
	}

	public void setDocumentFileName4(String documentFileName4) {
		this.documentFileName4 = documentFileName4;
	}

	public String getDocumentFileName5() {
		return documentFileName5;
	}

	public void setDocumentFileName5(String documentFileName5) {
		this.documentFileName5 = documentFileName5;
	}
	
	
	
}
