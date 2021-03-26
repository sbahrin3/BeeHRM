package lebah.db.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Shamsul Bahrin
 *
 */
@Entity
@Table(name="menus")
public class Menu {

	@Id @Column(name="id", length=100)
	private String id;
	@Column(length=100)
	private String title;
	@Column(length=100)
	private String icon;
	@Column(length=100)
	private String moduleClassName;
	private int orderNo;
	@ManyToOne
	private Menu parent;
	private int grouped;
    @OneToMany(fetch = FetchType.EAGER, mappedBy="parent")
 	private List<Menu> menus = new ArrayList<Menu>();
	
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "menu_roles",
            joinColumns = {@JoinColumn(name = "menu_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
	private List<Role> roles = new ArrayList<Role>();
    
    public Menu() {
    	setId(lebah.util.UIDGenerator.getUID());
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getModuleClassName() {
		return moduleClassName;
	}


	public void setModuleClassName(String moduleClassName) {
		this.moduleClassName = moduleClassName;
	}


	public int getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}


	public Menu getParent() {
		return parent;
	}


	public void setParent(Menu parent) {
		this.parent = parent;
	}


	public int getGrouped() {
		return grouped;
	}


	public void setGrouped(int grouped) {
		this.grouped = grouped;
	}


	public List<Menu> getMenus() {
		return menus;
	}
	
	public List<Menu> getSortedMenus() {
		Collections.sort(getMenus(), new SortMenu());
		return menus;
	}


	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public boolean hasRole(String roleId) {
		return roles.stream().filter(r -> r.getId().equals(roleId)).findFirst().isPresent();
	}
	
	public boolean hasRole(Role role) {
		return hasRole(role.getId());
	}
	
	public boolean hasRole(User user) {
		if ( hasRole(user.getRole())) return true;
		return user.getSecondaryRoles().stream().filter(r -> hasRole(r)).findFirst().isPresent();
	}  
	
	@Override
	public boolean equals(Object o) {
		Menu m = (Menu) o;
		return m.getId().equals(getId());
	}
	
	@Override
	public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (getId() != null ? getId().hashCode() : 0);
        return hash;
	}
	
	public boolean hasItems(User user) {
		boolean yes = false;
		for ( Menu m : menus ) {
			if ( m.hasRole(user)) {
				yes = true;
				break;
			}
		}

		return yes;
	}
	
	static class SortMenu implements Comparator<Menu> {
		@Override
		public int compare(Menu m1, Menu m2) {
			return m1.getOrderNo() < m2.getOrderNo() ?  -1 : 1;
		}
		
	}

}
